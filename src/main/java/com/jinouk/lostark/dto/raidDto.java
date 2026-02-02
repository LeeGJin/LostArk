package com.jinouk.lostark.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class raidDto {
    private String type;
    private String title;
    private List<difficultyDto> difficulty;

    public raidDto(String type, String title, List<difficultyDto>difficulty) {
        this.type = type;
        this.title = title;
        this.difficulty = difficulty;
    }
}
