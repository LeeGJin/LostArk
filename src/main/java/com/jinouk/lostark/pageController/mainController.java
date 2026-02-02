package com.jinouk.lostark.pageController;

import com.jinouk.lostark.dto.raidDto;
import com.jinouk.lostark.service.raidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class mainController {

    private final raidService raidService;

    @GetMapping("/goldCalculator")
    List<raidDto> getRaidList() {
        return raidService.getRaidList();
    }
}
