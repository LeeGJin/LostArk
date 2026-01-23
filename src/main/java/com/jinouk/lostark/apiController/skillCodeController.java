package com.jinouk.lostark.apiController;

import com.jinouk.lostark.dto.LostarkProfileIds;
import com.jinouk.lostark.service.LostarkProfileScrapeService;
import com.jinouk.lostark.service.LostarkSkillRecommendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class skillCodeController {

    private final LostarkProfileScrapeService profileService;
    private final LostarkSkillRecommendService skillService;

    public skillCodeController(
            LostarkProfileScrapeService profileService,
            LostarkSkillRecommendService skillService
    ) {
        this.profileService = profileService;
        this.skillService = skillService;
    }

    /**
     * nickname → ids → skillCode 원샷 API
     *
     * GET /skill-code-by-nickname?nickname=몌뿌치끼
     */
    @GetMapping("/skillcode")
    public Mono<String> getSkillCodeByNickname(@RequestParam String name) {
        return profileService.fetchIdsByNickname(name)
                .flatMap(this::toSkillCode);
    }

    // ---------------- 내부 연결 로직 ----------------

    private Mono<String> toSkillCode(LostarkProfileIds ids) {
        // 1️⃣ + 깨짐 방지: 혹시라도 공백으로 변한 값 복구
        String memberNo = restorePlus(ids.memberNo());
        String worldNo  = restorePlus(ids.worldNo());
        String pcId     = restorePlus(ids.pcId());

        // 2️⃣ SkillRecommend 호출
        return skillService.fetchSkillCode(memberNo, worldNo, pcId);
    }

    /**
     * Base64 계열 토큰에서
     * application/x-www-form-urlencoded 때문에
     * '+' → ' ' 로 변한 경우 복구
     */
    private static String restorePlus(String v) {
        return v == null ? null : v.replace(" ", "+");
    }
}