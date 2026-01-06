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

        List<difficultyDto> thaemine = new ArrayList<>();
        thaemine.add(new difficultyDto("Normal", 13000, 3500, Arrays.asList("어둠의 불","마력의 결정")));
        thaemine.add(new difficultyDto("Hard", 20000, 5500, Arrays.asList("어둠의 불","빛의 인도")));
        list.add(new raidDto("카멘",thaemine));

        List<difficultyDto> echidna = new ArrayList<>();
        echidna.add(new difficultyDto("Normal", 14500, 4000,Arrays.asList("아그리스의 눈","상급 재련 재료")));
        echidna.add(new difficultyDto("Hard", 20000, 3500, Arrays.asList("알키오네의 눈","상급 재련 재료")));
        list.add(new raidDto("에키드나",echidna));

        return list;
    }
}