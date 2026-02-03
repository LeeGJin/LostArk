package com.jinouk.lostark.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jinouk.lostark.apiController.charactersAPI;
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
public class updateRankingDto {
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

    public updateRankingDto(int rank, String name, String server, String characterClass, double itemLevel,
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
}
