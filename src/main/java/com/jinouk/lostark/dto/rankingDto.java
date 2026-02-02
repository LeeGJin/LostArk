package com.jinouk.lostark.dto;

import com.jinouk.lostark.entity.characterEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class rankingDto {
    private int rank;
    private String name;
    private String server;
    private String characterClass;
    private Double itemLevel;
    private Integer weaponLevel;
    private Integer combatPower;
    private String arkPassive;
    private String stats;
    private String guildName;
    private String updatedAt;

    // [표준화] DTO 변환 로직을 DTO 내부로 이동
    public static rankingDto from(characterEntity entity, int rank) {
        return rankingDto.builder()
                .rank(rank)
                .name(entity.getName())
                .server(entity.getServer())
                .characterClass(entity.getCharacterClass())
                .itemLevel(entity.getItemLevel())
                .weaponLevel(entity.getWeaponLevel())
                .combatPower(entity.getCombatPower())
                .arkPassive(entity.getArkPassive())
                .stats(entity.getStats())
                .guildName(entity.getGuildName())
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null)
                .build();
    }
}
