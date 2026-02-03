package com.jinouk.lostark.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jinouk.lostark.entity.characterEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "rank", "name", "server", "characterClass", "itemLevel",
        "weaponLevel", "combatPower", "stats", "arkPassive",
        "arkpassiveIconUrl", "guildName", "iconUrl"
})
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
    private String iconUrl;
    private String arkpassiveIconUrl;

    public rankingDto(int rank, String name, String server, String characterClass, double itemLevel,
                      Integer weaponLevel, Integer combatPower, String arkPassive, String stats, String guildName, String iconUrl, String arkpassiveIconUrl) {
        this.rank = rank;
        this.name = name;
        this.server = server;
        this.characterClass = characterClass;
        this.itemLevel = itemLevel;
        this.weaponLevel = weaponLevel;
        this.combatPower = combatPower;
        this.arkPassive = arkPassive;
        this.stats = stats;
        this.guildName = guildName;
        this.iconUrl = iconUrl;
        this.arkpassiveIconUrl = arkpassiveIconUrl;
    }
    public static rankingDto fromEntity(characterEntity entity, int rank) {
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
                .iconUrl(entity.getIconUrl())           // 엔티티에 저장된 캐릭터 이미지 주소
                .arkpassiveIconUrl(entity.getArkpassiveIconUrl()) // 엔티티에 저장된 아이콘 주소
                .build();
    }
}
