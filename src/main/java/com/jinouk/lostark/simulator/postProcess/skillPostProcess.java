package com.jinouk.lostark.simulator.postProcess;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class skillPostProcess {
    private String name;
    private Integer level;
    private String type;
    private String icon;

    // ✅ 추가: 스킬 자체 Tooltip(JSON 문자열)
    private String tooltip;

    private RuneDto rune;
    private List<TripodDto> selectedTripods;

    @Getter
    @Builder
    public static class TripodDto {
        private Integer tier;
        private Integer slot;
        private String name;
        private String icon;
        private String tooltip;
        private List<String> synergies; // ✅ 시너지 정보를 담기 위해 추가
    }

    @Getter
    @Builder
    public static class RuneDto {
        private String name;
        private String grade;
        private String icon;
        private String tooltip;
    }
}
