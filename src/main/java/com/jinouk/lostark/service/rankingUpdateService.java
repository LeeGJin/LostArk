package com.jinouk.lostark.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jinouk.lostark.dto.response.ArkPassiveResponse;
import com.jinouk.lostark.dto.response.EquipmentResponse;
import com.jinouk.lostark.dto.response.StatResponse;
import com.jinouk.lostark.dto.rankingDto;

import com.jinouk.lostark.entity.characterEntity;
import com.jinouk.lostark.repository.rankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor

public class rankingUpdateService {

    private final OtherService otherService;
    private final ObjectMapper objectMapper;
    private final rankingRepository rankingRepository; // DB 레포지토리 주입 추가
    private static final int MAX_RANKING_COUNT = 10000;


    //메인 메서드: API에서 데이터를 가져와 가공한 후 DB에 저장합니다.
    public Mono<rankingDto> processCharacterUpdate(String name) {
        return getUpdatedRanking(name) // 1. 아래 정의된 API 호출 및 DTO 변환 로직 실행
                .flatMap(this::updateOrInsertToDb); // 2. 가공된 DTO를 DB에 반영
    }


    //[DB 저장 로직] DTO를 엔티티로 변환하여 Upsert 및 1,0000명 제한 적용
    private Mono<rankingDto> updateOrInsertToDb(rankingDto dto) {
        return Mono.fromCallable(() -> {
            // 1. 기존 유저 확인 (Update)
            characterEntity existingUser = rankingRepository.findByName(dto.getName()).orElse(null);

            if (existingUser != null) {
                existingUser.updateFromDto(dto); // Dirty Checking으로 자동 업데이트
                rankingRepository.save(existingUser); // 명시적 저장 (생략 가능하나 안전함)
                return dto;
            }

            // 2. 신규 유저 등록 (Insert)
            long count = rankingRepository.count();

            if (count < MAX_RANKING_COUNT) {
                rankingRepository.save(characterEntity.fromDto(dto));
            } else {
                // 3. 1,000명 초과 시 최하위 아이템 레벨 유저와 교체
                // (아이템 레벨 기준 오름차순 첫 번째 = 꼴찌)
                characterEntity lowestUser = rankingRepository.findFirstByOrderByItemLevelAsc();

                if (dto.getItemLevel() > lowestUser.getItemLevel()) {
                    rankingRepository.delete(lowestUser);
                    rankingRepository.save(characterEntity.fromDto(dto));
                }
            }
            return dto;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    //기존 API 호출 및 DTO 가공 로직 (유지)
    public Mono<rankingDto> getUpdatedRanking(String name) {
        return Mono.zip(
                otherService.getArmoriesCharacterProfile(name),
                otherService.getArmoriesCharacterArkpassive(name),
                otherService.getArmoriesCharacterEquipment(name)
        ).map(tuple -> {
            try {
                StatResponse stat = objectMapper.readValue(tuple.getT1(), StatResponse.class);
                ArkPassiveResponse passive = objectMapper.readValue(tuple.getT2(), ArkPassiveResponse.class);
                List<EquipmentResponse> equipment = objectMapper.readValue(tuple.getT3(),
                        new TypeReference<List<EquipmentResponse>>() {});

                // rank는 DB에서 계산할 것이므로 0으로 전달
                return convertToDto(0, stat, passive, equipment);

            } catch (JsonProcessingException e) {
                throw new RuntimeException("데이터 파싱 실패: " + e.getMessage(), e);
            }
        });
    }

    private rankingDto convertToDto(int rank, StatResponse stat, ArkPassiveResponse passive, List<EquipmentResponse> equipments) {

        // 1. [가공] 스탯 추출 (치명/특화/신속 순서 고정 + 500 이상)
        List<String> priorityOrder = List.of("치명", "특화", "신속");
        String combinedStats = (stat.Stats() != null) ? stat.Stats().stream()
                .filter(s -> priorityOrder.contains(s.Type()))
                .filter(s -> {
                    String val = s.Value().replace(",", "");
                    return !val.isEmpty() && Double.parseDouble(val) >= 500;
                })
                .sorted(Comparator.comparingInt(s -> priorityOrder.indexOf(s.Type())))
                .map(StatResponse.StatDetail::Type)
                .collect(Collectors.joining("/")) : "";

        // 2. [가공] 아크 패시브 아이콘 추출
        String arkpassiveIcon = "";
        String passiveTitle = "미적용";

        if (passive != null && passive.Title() != null) {
            passiveTitle = passive.Title();

            // 비교를 위해 Title에서 공백 제거
            String cleanTitle = passiveTitle.replaceAll("\\s+", "").replace(":", "");

            arkpassiveIcon = passive.Effects().stream()
                    .filter(e -> e.Description() != null)
                    .filter(e -> {
                        // Description에서 HTML 태그 제거 및 공백/콜론 제거 후 비교
                        String cleanDesc = e.Description()
                                .replaceAll("<[^>]*>", "") // HTML 태그 제거
                                .replaceAll("\\s+", "")    // 공백 제거
                                .replace(":", "");         // 콜론 제거
                        return cleanDesc.contains(cleanTitle);
                    })
                    .map(ArkPassiveResponse.Effect::Icon)
                    .findFirst()
                    .orElse("");
        }

        // 3. [가공] 무기 레벨 추출
        Integer weaponLvl = (equipments != null) ? equipments.stream()
                .filter(e -> "무기".equals(e.Type()))
                .map(e -> {
                    // 1. 숫자만 추출
                    String levelStr = e.Name().replaceAll("[^0-9]", "");
                    if (levelStr.isEmpty()) return 0;

                    // 2. 일단 앞의 두 자리까지만 고려하여 숫자로 변환
                    if (levelStr.length() > 2) {
                        levelStr = levelStr.substring(0, 2);
                    }
                    int level = Integer.parseInt(levelStr);

                    // 3. 25를 초과하면 앞의 한 자리만 추출 (예: 30 -> 3, 99 -> 9)
                    if (level > 25) {
                        level = Integer.parseInt(levelStr.substring(0, 1));
                    }

                    return level;
                })
                .findFirst()
                .orElse(0) : 0;

        // 4. [가공] 아이템 레벨 및 전투력 변환
        Double itemLevel = parseDoubleSafely(stat.ItemAvgLevel());
        Integer combatPower = (int) Math.floor(parseDoubleSafely(stat.CombatPower()));

        return new rankingDto(
                rank,
                stat.CharacterName(),
                stat.ServerName(),
                stat.CharacterClassName(),
                itemLevel,
                weaponLvl,
                combatPower,
                passiveTitle,
                combinedStats,
                stat.GuildName(),
                stat.CharacterImage(),
                arkpassiveIcon // 추가된 필드 주입
        );
    }


    private double parseDoubleSafely(String value) {
        if (value == null || value.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(value.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}