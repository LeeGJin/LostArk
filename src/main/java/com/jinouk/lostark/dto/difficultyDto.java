package com.jinouk.lostark.dto;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter

public class difficultyDto {
    private String difficulty;
    private String gate;
    private int level;
    private int gold;
    private int extraRewardCost;

    public difficultyDto(String difficulty, String gate, int level, int gold, int extraRewardCost) {
        this.difficulty = difficulty;
        this.gate = gate;
        this.level = level;
        this.gold = gold;
        this.extraRewardCost = extraRewardCost;
    }
}
