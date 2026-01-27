package com.jinouk.lostark.simulator.service;

import com.jinouk.lostark.service.armoriesAPIService;
import com.jinouk.lostark.simulator.postProcess.skillPostProcess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class simulatorService {

    private final armoriesAPIService armoriesAPIService;

    // com.jinouk.lostark.simulator.service.simulatorService

    public Mono<List<skillPostProcess>> doProcess(String name) {
        return armoriesAPIService.getArmoriesCharacterCombatSkills(name)
                .map(list -> list.stream()

                        .filter(item -> item.getLevel() != null && item.getLevel() > 1)

                        .map(item -> {
                            var rune = item.getRune() == null ? null : skillPostProcess.RuneDto.builder()
                                    .name(item.getRune().getName())
                                    .grade(item.getRune().getGrade())
                                    .icon(item.getRune().getIcon())
                                    // ✅ 여기
                                    .tooltip(item.getRune().getTooltip())
                                    .build();

                            var selectedTripods = item.getTripods() == null
                                    ? List.<skillPostProcess.TripodDto>of()
                                    : item.getTripods().stream()
                                    .filter(t -> Boolean.TRUE.equals(t.getIsSelected()))
                                    .map(t -> skillPostProcess.TripodDto.builder()
                                            .tier(t.getTier())
                                            .slot(t.getSlot())
                                            .name(t.getName())
                                            .icon(t.getIcon())
                                            // ✅ 여기
                                            .tooltip(t.getTooltip())
                                            .build())
                                    .toList();

                            return skillPostProcess.builder()
                                    .name(item.getName())
                                    .level(item.getLevel())
                                    .type(item.getType())
                                    .icon(item.getIcon())
                                    .tooltip(item.getTooltip()) // ✅ 추가
                                    .rune(rune)
                                    .selectedTripods(selectedTripods)
                                    .build();

                        })
                        .toList());
    }
}
