package com.jinouk.lostark.apiParse.apiParseService;

import com.jinouk.lostark.apiParse.parseDto.equipmentWeaponDto;
import com.jinouk.lostark.apiParse.parseDto.equipmentWeaponEstherDto;
import com.jinouk.lostark.service.armoriesAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class equipmentWeaponParseService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient loawebclient;

    public Mono<String> getAndProcessWeapon(String characterName) {
        armoriesAPIService apiService = new armoriesAPIService(loawebclient);

        return apiService.getArmoriesCharacterEquipment(characterName)
                .doOnNext(jsonString -> {
                    try {
                        JsonNode rootArray = objectMapper.readTree(jsonString);

                        for (JsonNode item : rootArray) {
                            if ("무기".equals(item.path("Type").asText())) {
                                String grade = item.path("Grade").asText();
                                String tooltipRaw = item.path("Tooltip").asText();
                                System.out.println("tooltip: " + tooltipRaw);
                                JsonNode tooltip = objectMapper.readTree(tooltipRaw);

                                if ("에스더".equals(grade)) {
                                    processEstherWeapon(item, tooltip);
                                } else {
                                    processNormalWeapon(item, tooltip);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("JSON 가공 중 오류: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    private void processEstherWeapon(JsonNode item, JsonNode tooltip) {
        equipmentWeaponEstherDto dto = new equipmentWeaponEstherDto();

        try {
            dto.setName(item.path("Name").asText("이름 없음"));
            dto.setGrade("에스더");
            dto.setQuality(tooltip.path("Element_001").path("value").path("qualityValue").asInt(0));

            equipmentWeaponEstherDto.statsDto stats = new equipmentWeaponEstherDto.statsDto();
            stats.setAttackPower(parseNumber(tooltip.path("Element_005").path("value").path("Element_001").asText("")));
            stats.setAdditionalDamagePercent(parseDouble(tooltip.path("Element_007").path("value").path("Element_001").asText("")));
            dto.setStats(stats);


            JsonNode estherNode = tooltip.path("Element_008").path("value").path("Element_000");

            if (!estherNode.isMissingNode()) {
                equipmentWeaponEstherDto.estherDto estherInfo = new equipmentWeaponEstherDto.estherDto();

                String topStr = estherNode.path("topStr").asText("");
                estherInfo.setName(extractBetweenBrackets(topStr));

                List<equipmentWeaponEstherDto.linkEffectDto> effects = new ArrayList<>();
                JsonNode contents = estherNode.path("contentStr");

                if (contents.has("Element_001")) effects.add(parseLinkEffect(contents.path("Element_001").path("contentStr").asText("")));
                if (contents.has("Element_002")) effects.add(parseLinkEffect(contents.path("Element_002").path("contentStr").asText("")));

                estherInfo.setEffects(effects);
                dto.setEsther(estherInfo);
            }

            System.out.println("\n========================================");
            System.out.println("       [ ESTHER WEAPON DATA ]         ");
            System.out.println("========================================");
            System.out.println("▶ 아이템명  : " + dto.getName());
            System.out.println("▶ 등급  : " + dto.getGrade());
            System.out.println("▶ 퀄리티  : " + dto.getQuality());
            System.out.println("----------------------------------------");
            System.out.println("▶ 무기공격력: " + dto.getStats().getAttackPower());
            System.out.println("▶ 추가피해  : " + dto.getStats().getAdditionalDamagePercent() + "%");
            System.out.println("----------------------------------------");

            if (dto.getEsther() != null) {
                // 이름이 비어있을 경우 원본 topStr를 확인하기 위한 디버깅 포함
                String estherName = dto.getEsther().getName();
                System.out.println("▶ 전용에스더: " + (estherName.isEmpty() ? "추출 실패 (topStr 확인 필요)" : estherName));
                System.out.println("----------------------------------------");

                for (equipmentWeaponEstherDto.linkEffectDto e : dto.getEsther().getEffects()) {
                    System.out.println("▶ 결속 효과: " + e.getName());
                    System.out.println("   - 지속시간: " + e.getDurationSeconds() + "초");
                    if (e.getStatIncrease() != null) {
                        System.out.println("   - [1단계] 스탯 증가량(strength): " + e.getStatIncrease().getStrength());
                        System.out.println("   - [1단계] 스탯 증가량(dexterity): " + e.getStatIncrease().getDexterity());
                        System.out.println("   - [1단계] 스탯 증가량(intelligence): " + e.getStatIncrease().getIntelligence());

                    }
                    if (e.getPartyRangeMeter() != null) {
                        System.out.println("   - [2단계] 파티 범위: " + e.getPartyRangeMeter() + "m");
                        System.out.println("   - [2단계] 공격력 증가: " + e.getPartyAttackPowerIncrease());
                    }
                    System.out.println("----------------------------------------");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processNormalWeapon(JsonNode item, JsonNode tooltip) {
        String fullName = item.path("Name").asText();
        int enhancementLevel = Integer.parseInt(fullName.split(" ")[0].replace("+", ""));
        int quality = tooltip.path("Element_001").path("value").path("qualityValue").asInt();

        int advLevel = parseNumber(tooltip.path("Element_005").path("value").asText());
        int atkPower = parseNumber(tooltip.path("Element_006").path("value").path("Element_001").asText());
        double addDmg = parseDouble(tooltip.path("Element_008").path("value").path("Element_001").asText());

        equipmentWeaponDto dto = new equipmentWeaponDto(enhancementLevel, quality, advLevel, atkPower, addDmg);
        System.out.println("--- 일반 무기 파싱 결과 (" + fullName + ") ---");
        System.out.println("재련단계: " + dto.getEnhancementLevel());
        System.out.println("품질: " + dto.getQuality());
        System.out.println("상급재련단계: " + dto.getAdvancedEnhancementLevel());
        System.out.println("무공: " + dto.getAttackPower());
        System.out.println("추피: " + dto.getAdditionalDamagePercent());
    }

    private equipmentWeaponEstherDto.linkEffectDto parseLinkEffect(String rawText) {
        equipmentWeaponEstherDto.linkEffectDto effect = new equipmentWeaponEstherDto.linkEffectDto();
        String cleanText = rawText.replaceAll("<[^>]*>", ""); // 태그 제거

        Pattern namePattern = Pattern.compile("'(.*?)'");
        Matcher nameMatcher = namePattern.matcher(cleanText);
        if (nameMatcher.find()) effect.setName(nameMatcher.group(1));

        List<Integer> numbers = new ArrayList<>();
        Matcher numMatcher = Pattern.compile("\\d+").matcher(cleanText);
        while (numMatcher.find()) {
            numbers.add(Integer.parseInt(numMatcher.group()));
        }

        if (!numbers.isEmpty()) {
            effect.setDurationSeconds(numbers.get(0));

            if (cleanText.contains("힘, 민첩, 지능")) { // 1단계 결속
                equipmentWeaponEstherDto.statIncreaseDto si = new equipmentWeaponEstherDto.statIncreaseDto();
                int statVal = numbers.get(1);
                si.setStrength(statVal);
                si.setDexterity(statVal);
                si.setIntelligence(statVal);
                effect.setStatIncrease(si);
            } else if (cleanText.contains("파티원")) { // 2단계 결속
                effect.setPartyRangeMeter(numbers.get(1));
                effect.setPartyAttackPowerIncrease(numbers.get(2));
            }
        }
        return effect;
    }

    private String extractBetweenBrackets(String text) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : "";
    }

    private int parseNumber(String text) {
        if (text == null || text.isEmpty()) return 0;
        String digits = text.replaceAll("<[^>]*>", "").replaceAll("[^0-9]", "");
        return digits.isEmpty() ? 0 : Integer.parseInt(digits);
    }

    private double parseDouble(String text) {
        if (text == null || text.isEmpty()) return 0.0;
        String cleaned = text.replaceAll("<[^>]*>", "").replaceAll("[^0-9.]", "");
        return cleaned.isEmpty() ? 0.0 : Double.parseDouble(cleaned);
    }
}