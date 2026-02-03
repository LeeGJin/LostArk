package com.jinouk.lostark.apiController;

import com.jinouk.lostark.dto.updateRankingDto;
import com.jinouk.lostark.service.rankingService;
import com.jinouk.lostark.service.rankingUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking") // 랭킹 관련 API 경로 표준화
public class rankingController {

    private final rankingService rankingService;
    private final rankingUpdateService rankingUpdateService;

    //characters 데이터 베이스 업데이트
    @PostMapping("/update/{name}")
    public Mono<updateRankingDto> updateCharacterData(@PathVariable String name) {
        return rankingUpdateService.processCharacterUpdate(name);
    }

     //아이템 레벨 기준 전체 랭킹 조회
    @GetMapping("/item-level")
    public Flux<updateRankingDto> getItemLevelRanking(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return rankingService.getItemLevelRankings(page, size);
    }

     //직업별 전투력 기준 랭킹 조회
    @GetMapping("/class")
    public Flux<updateRankingDto> getClassRanking(
            @RequestParam(value = "className") String className, // 파라미터명 명시
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        return rankingService.getClassRankings(className, page, size);
    }

    //전투력 기준 전체 랭킹 조회
    @GetMapping("/combat-power")
    public Flux<updateRankingDto> getCombatPowerRanking(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        // 서비스 메서드명이 getCombatPowerRankings(복수형)인지 확인 필요
        return rankingService.getCombatPowerRankings(page, size);
    }
}
