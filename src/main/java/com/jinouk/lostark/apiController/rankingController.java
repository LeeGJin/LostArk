package com.jinouk.lostark.apiController;

import com.jinouk.lostark.dto.rankingDto;
import com.jinouk.lostark.dto.response.ArkPassiveResponse;
import com.jinouk.lostark.dto.response.EquipmentResponse;
import com.jinouk.lostark.dto.response.StatResponse;
import com.jinouk.lostark.dto.updateRankingDto;
import com.jinouk.lostark.service.rankingService;
import com.jinouk.lostark.service.updateRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking") // 랭킹 관련 API 경로 표준화
public class rankingController {

    private final rankingService rankingService;
    private final updateRankingService updateRankingService;

    @GetMapping("/update/{name}")
    public Mono<updateRankingDto> updateCharacterData(@PathVariable String name) {
        // 이제 rank 없이 이름만 넘깁니다.
        return updateRankingService.getUpdatedRanking(name);
    }

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
