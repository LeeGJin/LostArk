package com.jinouk.lostark.apiParse.parseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class characterProfileDto {

    //치명
    private final int Critical;
    //특화
    private final int Specialization;
    //신속
    private final int Swiftness;
    //제압
    private final int Domination;
    //인내
    private final int Endurance;
    //숙련
    private final int Expertise;

    public characterProfileDto(int Critical, int Specialization, int Swiftness, int Domination, int Endurance, int Expertise) {
        this.Critical = Critical;
        this.Specialization = Specialization;
        this.Swiftness = Swiftness;
        this.Domination = Domination;
        this.Endurance = Endurance;
        this.Expertise = Expertise;
    }

}
