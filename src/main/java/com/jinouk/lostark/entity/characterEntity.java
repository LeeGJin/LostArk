package com.jinouk.lostark.entity;

import com.jinouk.lostark.dto.rankingDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "server", nullable = false, length = 20)
    private String server;

    @Column(name = "class", length = 30)
    private String characterClass;

    @Column(name = "item_level")
    private Double itemLevel;

    @Column(name = "weapon_level")
    private Integer weaponLevel;

    @Column(name = "combat_power")
    private Integer combatPower;

    @Column(name = "ark_passive", columnDefinition = "json")
    private String arkPassive;

    @Column(name = "stats", columnDefinition = "json")
    private String stats;

    @Column(name = "guild_name", length = 50)
    private String guildName;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    /**
     * [표준화] DTO 데이터를 엔티티에 반영하는 업데이트 로직
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
        // updatedAt은 DB의 ON UPDATE CURRENT_TIMESTAMP에 의해 자동 갱신됩니다.
    }

    /**
     * [표준화] DTO를 엔티티 객체로 변환하는 정적 메서드
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
                .build();
    }
}
