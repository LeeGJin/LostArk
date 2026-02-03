package com.jinouk.lostark.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.jinouk.lostark.dto.response.ArkPassiveResponse;
import com.jinouk.lostark.dto.response.EquipmentResponse;
import com.jinouk.lostark.dto.response.StatResponse;
import com.jinouk.lostark.dto.updateRankingDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class updateRankingService {

    private final OtherService otherService;
    private final ObjectMapper objectMapper;

    public Mono<updateRankingDto> getUpdatedRanking(String name) {
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

                return convertToDto(0, stat, passive, equipment);

            } catch (JsonProcessingException e) {
                throw new RuntimeException("로스트아크 API 데이터 파싱 실패: " + e.getMessage(), e);
            }
        });
    }

    private updateRankingDto convertToDto(int rank, StatResponse stat, ArkPassiveResponse passive, List<EquipmentResponse> equipments) {

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
            // Effects 리스트에서 Description에 Title(예: 광전사의 비기)이 포함된 항목의 아이콘 찾기
            arkpassiveIcon = passive.Effects().stream()
                    .filter(e -> e.Description() != null && e.Description().contains(passive.Title()))
                    .map(ArkPassiveResponse.Effect::Icon)
                    .findFirst()
                    .orElse("");
        }

        // 3. [가공] 무기 레벨 추출
        Integer weaponLvl = (equipments != null) ? equipments.stream()
                .filter(e -> "무기".equals(e.Type()))
                .map(e -> {
                    String levelStr = e.Name().replaceAll("[^0-9]", "");
                    return levelStr.isEmpty() ? 0 : Integer.parseInt(levelStr);
                })
                .findFirst()
                .orElse(0) : 0;

        // 4. [가공] 아이템 레벨 및 전투력 변환
        Double itemLevel = parseDoubleSafely(stat.ItemAvgLevel());
        Integer combatPower = (int) Math.floor(parseDoubleSafely(stat.CombatPower()));

        return new updateRankingDto(
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