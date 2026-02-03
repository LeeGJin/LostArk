package com.jinouk.lostark.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ArkPassiveResponse(
        String Title,
        Boolean IsArkPassive,
        List<Effect> Effects // API 응답의 Effects 리스트를 받기 위해 추가
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Effect(
            String Name,
            String Description,
            String Icon
    ) {}
}