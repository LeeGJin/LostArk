package com.jinouk.lostark.service;

import com.jinouk.lostark.dto.rankingDto;
import com.jinouk.lostark.repository.rankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class rankingService {

    private final rankingRepository rankingRepository;
    private static final int MAX_RANKING_COUNT = 1000;

    // --- 기존 조회 로직 (표준화 유지) ---

    /**
     * 1. 아이템 레벨 기준 랭킹 조회
     */
    public Flux<rankingDto> getItemLevelRankings(int page, int size) {
        int offset = (page - 1) * size;
        return Mono.fromCallable(() ->
                        rankingRepository.findAllByOrderByItemLevelDesc(PageRequest.of(page - 1, size)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable) // List를 Flux로 바로 변환
                .index()
                .map(tuple -> rankingDto.fromEntity(tuple.getT2(), (int) (offset + tuple.getT1() + 1)));
    }

    /**
     * 2. 전투력 기준 랭킹 조회
     */
    public Flux<rankingDto> getCombatPowerRankings(int page, int size) {
        int offset = (page - 1) * size;
        return Mono.fromCallable(() ->
                        rankingRepository.findAllByOrderByCombatPowerDesc(PageRequest.of(page - 1, size)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .index()
                .map(tuple -> rankingDto.fromEntity(tuple.getT2(), (int) (offset + tuple.getT1() + 1)));
    }

    /**
     * 3. 직업별 전투력 순위 조회
     */
    public Flux<rankingDto> getClassRankings(String characterClass, int page, int size) {
        int offset = (page - 1) * size;
        return Mono.fromCallable(() ->
                        rankingRepository.findAllByCharacterClassOrderByCombatPowerDesc(characterClass, PageRequest.of(page - 1, size)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .index()
                .map(tuple -> rankingDto.fromEntity(tuple.getT2(), (int) (offset + tuple.getT1() + 1)));
    }

    public Flux<rankingDto> getArkpassiveRankings(String className, String arkPassive,int page, int size) {
        int offset = (page - 1) * size;
        return Mono.fromCallable(() ->
                        rankingRepository.findByCharacterClassAndArkPassiveOrderByCombatPowerDesc(className, arkPassive,PageRequest.of(page - 1, size)))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable)
                .index()
                .map(tuple -> rankingDto.fromEntity(tuple.getT2(), (int) (offset + tuple.getT1() + 1)));
    }
}