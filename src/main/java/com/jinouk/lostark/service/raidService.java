package com.jinouk.lostark.service;

import com.jinouk.lostark.tlqkf_dto.raidDto;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class raidService {

    public List<raidDto> getRaidList() {
        List<raidDto> list = new ArrayList<>();

        list.add(new raidDto("카멘",13000,true));
        list.add(new raidDto("카멘",30000, false));

        list.add(new raidDto("에드키나",14500,true));
        list.add(new raidDto("에드키나",20000,false));

        return list;
    }
}