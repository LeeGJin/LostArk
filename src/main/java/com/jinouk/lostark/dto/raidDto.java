package com.jinouk.lostark.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class raidDto {
    private String title;
    private List<difficultyDto> difficulty;

    public raidDto(String title, List<difficultyDto>difficulty) {
        this.title = title;
        this.difficulty = difficulty;
    }
}
