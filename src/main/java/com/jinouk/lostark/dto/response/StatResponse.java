package com.jinouk.lostark.dto.response;

import lombok.Getter;
import java.util.List;

public record StatResponse(
        String CharacterClassName,
        String CharacterImage,
        String CharacterName,
        String CombatPower,
        String ItemAvgLevel,
        String ServerName,
        List<StatDetail> Stats,
        String GuildName
) {
    public record StatDetail(
            String Type,
            String Value
    ) {}
}
