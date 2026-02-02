package com.jinouk.lostark.apiController;

import com.jinouk.lostark.dto.rankingDto;
import com.jinouk.lostark.simulator.service.rankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking") // 랭킹 관련 API 경로 표준화
public class rankingController {

    private final rankingService rankingService;

    /**
     * 아이템 레벨 기준 전체 랭킹 조회
     */
    @GetMapping("/item-level")
    public Flux<rankingDto> getItemLevelRanking(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return rankingService.getItemLevelRankings(page, size);
    }

    /**
     * 직업별 전투력 기준 랭킹 조회
     */
    @GetMapping("/class")
    public Flux<rankingDto> getClassRanking(
            @RequestParam String className,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return rankingService.getClassRankings(className, page, size);
    }

    /**
     * 전투력 기준 전체 랭킹 조회
     */
    @GetMapping("/combat-power")
    public Flux<rankingDto> getCombatPowerRanking(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return rankingService.getCombatPowerRanking(page, size);
    }
}
