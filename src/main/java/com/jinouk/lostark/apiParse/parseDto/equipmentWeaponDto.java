package com.jinouk.lostark.apiParse.parseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class equipmentWeaponDto {

    //재련단계
    private final int enhancementLevel;
    //품질
    private final int quality;
    //상급재련단계
    private final int advancedEnhancementLevel;
    //무공
    private final int attackPower;
    //추피
    private final double additionalDamagePercent;

    public equipmentWeaponDto(int enhancementLevel, int quality, int advancedEnhancementLevel, int attackPower,double additionalDamagePercent) {
        this.enhancementLevel = enhancementLevel;
        this.quality = quality;
        this.advancedEnhancementLevel = advancedEnhancementLevel;
        this.attackPower = attackPower;
        this.additionalDamagePercent = additionalDamagePercent;
    }
}
