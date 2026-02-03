package com.jinouk.lostark.entity;

import com.jinouk.lostark.dto.rankingDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "characters")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class characterEntity {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "server")
    private String server;

    @Column(name = "class") // 말씀하신 'class' 칼럼명 반영
    private String characterClass;

    @Column(name = "item_level")
    private Double itemLevel;

    @Column(name = "weapon_level")
    private Integer weaponLevel;

    @Column(name = "combat_power")
    private Integer combatPower;

    @Column(name = "ark_passive")
    private String arkPassive;

    @Column(name = "stats")
    private String stats;

    @Column(name = "guild_name")
    private String guildName;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "ark_passive_icon_url")
    private String arkpassiveIconUrl;

    @PrePersist
    @PreUpdate
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * DTO 데이터를 엔티티에 반영 (Update 시 사용)
     */
    public void updateFromDto(rankingDto dto) {
        this.server = dto.getServer();
        this.characterClass = dto.getCharacterClass();
        this.itemLevel = dto.getItemLevel();
        this.weaponLevel = dto.getWeaponLevel();
        this.combatPower = dto.getCombatPower();
        this.arkPassive = dto.getArkPassive();
        this.stats = dto.getStats();
        this.guildName = dto.getGuildName();
        this.iconUrl = dto.getIconUrl();
        this.arkpassiveIconUrl = dto.getArkpassiveIconUrl();
        // updatedAt은 @PreUpdate를 통해 자동 갱신
    }

    /**
     * DTO를 엔티티 객체로 변환 (Insert 시 사용)
     */
    public static characterEntity fromDto(rankingDto dto) {
        return characterEntity.builder()
                .name(dto.getName())
                .server(dto.getServer())
                .characterClass(dto.getCharacterClass())
                .itemLevel(dto.getItemLevel())
                .weaponLevel(dto.getWeaponLevel())
                .combatPower(dto.getCombatPower())
                .arkPassive(dto.getArkPassive())
                .stats(dto.getStats())
                .guildName(dto.getGuildName())
                .iconUrl(dto.getIconUrl())
                .arkpassiveIconUrl(dto.getArkpassiveIconUrl())
                .build();
    }
}