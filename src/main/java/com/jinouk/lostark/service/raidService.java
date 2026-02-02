package com.jinouk.lostark.service;

import com.jinouk.lostark.dto.difficultyDto;
import com.jinouk.lostark.dto.raidDto;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class raidService {

    public List<raidDto> getRaidList() {
        List<raidDto> list = new ArrayList<>();

        List<difficultyDto> Serka = new ArrayList<>();
        Serka.add(new difficultyDto("NightMare", "1관문",1740, 21000, 6720));
        Serka.add(new difficultyDto("NightMare", "2관문",1740, 33000, 10560));
        Serka.add(new difficultyDto("Hard", "1관문",1730, 17500, 5600));
        Serka.add(new difficultyDto("Hard", "2관문",1730, 26500, 8480));
        Serka.add(new difficultyDto("Normal", "1관문",1710, 14000, 4480));
        Serka.add(new difficultyDto("Normal", "2관문",1710, 21000, 6720));
        list.add(new raidDto("그림자 레이드","세르카",Serka));
        //종막
        List<difficultyDto> Kazeroth = new ArrayList<>();
        Kazeroth.add(new difficultyDto("Hard", "1관문",1730, 17000, 5440));
        Kazeroth.add(new difficultyDto("Hard", "2관문",1730, 35000, 11200));
        Kazeroth.add(new difficultyDto("Normal", "1관문",1710, 14000, 4480));
        Kazeroth.add(new difficultyDto("Normal", "2관문",1710, 26000, 8320));
        list.add(new raidDto("종막","카제로스",Kazeroth));
        //4막
        List<difficultyDto> Armoche = new ArrayList<>();
        Armoche.add(new difficultyDto("Hard", "1관문",1720, 15000, 4800));
        Armoche.add(new difficultyDto("Hard", "2관문",1720, 27000, 8640));
        Armoche.add(new difficultyDto("Normal", "1관문",1700, 12500, 4000));
        Armoche.add(new difficultyDto("Normal", "2관문",1700, 20500, 6560));
        list.add(new raidDto("4막","아르모체",Armoche));
        //3막
        List<difficultyDto> Mordum = new ArrayList<>();
        Mordum.add(new difficultyDto("Hard", "1관문",1700, 5000, 1650));
        Mordum.add(new difficultyDto("Hard", "2관문",1700, 8000, 2640));
        Mordum.add(new difficultyDto("Hard", "3관문",1700, 14000, 4060));
        Mordum.add(new difficultyDto("Normal", "1관문",1680, 4000, 1300));
        Mordum.add(new difficultyDto("Normal", "2관문",1680, 7000, 2350));
        Mordum.add(new difficultyDto("Normal", "3관문",1680, 10000, 3360));
        Mordum.add(new difficultyDto("Single", "1관문",1680, 4000, 1300));
        Mordum.add(new difficultyDto("Single", "2관문",1680, 7000, 2350));
        Mordum.add(new difficultyDto("Single", "3관문",1680, 10000, 3360));
        list.add(new raidDto("3막","모르둠",Mordum));
        //2막
        List<difficultyDto> Brelshaza = new ArrayList<>();
        Brelshaza.add(new difficultyDto("Hard", "1관문",1690, 7500, 2400));
        Brelshaza.add(new difficultyDto("Hard", "2관문",1690, 15500, 5100));
        Brelshaza.add(new difficultyDto("Normal", "1관문",1670, 5500, 1820));
        Brelshaza.add(new difficultyDto("Normal", "2관문",1670, 11000, 3720));
        Brelshaza.add(new difficultyDto("Single", "1관문",1670, 5500, 1820));
        Brelshaza.add(new difficultyDto("Single", "2관문",1670, 11000, 3720));
        list.add(new raidDto("2막","아브렐슈드",Brelshaza));
        //1막
        List<difficultyDto> Aegir = new ArrayList<>();
        Aegir.add(new difficultyDto("Hard", "1관문",1680, 5500, 1820));
        Aegir.add(new difficultyDto("Hard", "2관문",1680, 12500, 4150));
        Aegir.add(new difficultyDto("Normal", "1관문",1660, 3500, 750));
        Aegir.add(new difficultyDto("Normal", "2관문",1660, 8000, 1780));
        Aegir.add(new difficultyDto("Single", "1관문",1660, 3500, 750));
        Aegir.add(new difficultyDto("Single", "2관문",1660, 8000, 1780));
        list.add(new raidDto("1막","에기르",Aegir));
        //서막
        List<difficultyDto> Echidna = new ArrayList<>();
        Echidna.add(new difficultyDto("Hard", "1관문",1640, 2200, 720));
        Echidna.add(new difficultyDto("Hard", "2관문",1640, 5000, 1630));
        Echidna.add(new difficultyDto("Normal", "1관문",1620, 1900, 310));
        Echidna.add(new difficultyDto("Normal", "2관문",1620, 4200, 700));
        Echidna.add(new difficultyDto("Single", "1관문",1620, 1900, 310));
        Echidna.add(new difficultyDto("Single", "2관문",1620, 4200, 700));
        list.add(new raidDto("서막","에키드나",Echidna));

        return list;
    }
}