package com.jinouk.lostark.simulator.service;

import com.jinouk.lostark.dto.rankingDto;
import com.jinouk.lostark.entity.characterEntity;
import com.jinouk.lostark.simulator.repository.rankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class rankingService {

    private final rankingRepository rankingRepository;
    private static final int MAX_RANKING_COUNT = 1000;

    /**
     * [신규] 유저 검색 시 호출되는 Upsert 및 Top 1000 관리 로직
     */
    @Transactional
    public Mono<Void> updateOrInsertUser(rankingDto dto) {
        return Mono.fromCallable(() -> {
            // 1. 기존 유저 확인
            characterEntity existingUser = rankingRepository.findByName(dto.getName()).orElse(null);

            if (existingUser != null) {
                // 이미 있다면 정보 업데이트 (Dirty Checking)
                existingUser.updateFromDto(dto);
                return null;
            }

            // 2. 신규 유저라면 1000명 제한 체크
            long count = rankingRepository.count();

            if (count < MAX_RANKING_COUNT) {
                // 자리가 있으면 바로 저장
                rankingRepository.save(characterEntity.fromDto(dto));
            } else {
                // 3. 자리가 없으면 최하위 전투력 유저와 비교
                characterEntity lowestUser = rankingRepository.findFirstByOrderByCombatPowerAsc();

                if (dto.getCombatPower() > lowestUser.getCombatPower()) {
                    // 검색된 유저가 더 강하면 꼴찌 삭제 후 새 유저 저장
                    rankingRepository.delete(lowestUser);
                    // delete와 save가 한 트랜잭션 내에서 처리됨
                    rankingRepository.save(characterEntity.fromDto(dto));
                }
            }
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    // --- 기존 조회 로직 (표준화 유지) ---

    public Flux<rankingDto> getItemLevelRankings(int page, int size) {
        int offset = (page - 1) * size;
        return Mono.fromCallable(() ->
                        rankingRepository.findAllByOrderByItemLevelDesc(PageRequest.of(page - 1, size)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .index()
                .map(tuple -> rankingDto.from(tuple.getT2(), (int) (offset + tuple.getT1() + 1)));
    }

    public Flux<rankingDto> getClassRankings(String className, int page, int size) {
        int offset = (page - 1) * size;
        return Mono.fromCallable(() ->
                        rankingRepository.findAllByCharacterClassOrderByCombatPowerDesc(className, PageRequest.of(page - 1, size)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .index()
                .map(tuple -> rankingDto.from(tuple.getT2(), (int) (offset + tuple.getT1() + 1)));
    }

    public Flux<rankingDto> getCombatPowerRanking(int page, int size) {
        int offset = (page - 1) * size;
        return Mono.fromCallable(() ->
                        rankingRepository.findAllByOrderByCombatPowerDesc(PageRequest.of(page - 1, size)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .index()
                .map(tuple -> rankingDto.from(tuple.getT2(), (int) (offset + tuple.getT1() + 1)));
    }
}