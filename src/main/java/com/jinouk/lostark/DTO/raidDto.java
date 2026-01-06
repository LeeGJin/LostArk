package com.jinouk.lostark.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class raidDto {
    private String title;
    private int gold;
    private boolean type;

    public raidDto(String title, int gold, boolean type) {
        this.title = title;
        this.gold = gold;
        this.type = type;
    }
}
