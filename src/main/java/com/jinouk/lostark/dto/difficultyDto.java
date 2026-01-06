package com.jinouk.lostark.dto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter

public class difficultyDto {
    private String type;
    private int gold;
    private int extraRewardCost;
    private List<String> mandatoryDrops;

    public difficultyDto(String type, int gold, int extraRewardCost, List<String> mandatoryDrops) {
        this.type = type;
        this.gold = gold;
        this.extraRewardCost = extraRewardCost;
        this.mandatoryDrops = mandatoryDrops;
    }
}
