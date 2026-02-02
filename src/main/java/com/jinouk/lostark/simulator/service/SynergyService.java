package com.jinouk.lostark.simulator.service;

import com.jinouk.lostark.simulator.dto.SynergyRequest;
import com.jinouk.lostark.simulator.entity.SkillDetail;
import com.jinouk.lostark.simulator.entity.SynergyType;
import com.jinouk.lostark.simulator.repository.SkillDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SynergyService {

    private final SkillDetailRepository skillDetailRepository;

    @Transactional(readOnly = true)
    public Map<String, List<String>> getBulkSynergies(List<SynergyRequest> requests) {
        // [1] 요청 데이터에서 고유한 스킬명 추출 및 세척
        List<String> skillNames = requests.stream()
                .map(req -> sanitizeName(req.skillName()))
                .distinct()
                .toList();

        // [2] DB 조회 (Repository의 @EntityGraph 덕분에 시너지까지 한 번에 조회됨)
        List<SkillDetail> matchedDetails = skillDetailRepository.findBySkillNameIn(skillNames);

        Map<String, List<String>> resultMap = new HashMap<>();

        // [3] 매칭 로직 수행
        for (SynergyRequest req : requests) {
            String sName = sanitizeName(req.skillName());
            String tName = sanitizeName(req.tripodName());

            List<String> synergies = matchedDetails.stream()
                    .filter(sd -> sanitizeName(sd.getSkillName()).equals(sName) &&
                            sanitizeName(sd.getTripodName()).equals(tName))
                    .flatMap(sd -> sd.getSynergies().stream())
                    .map(SynergyType::getDescription)
                    .toList();

            if (!synergies.isEmpty()) {
                // 프론트엔드 식별을 위해 "원본 스킬명|원본 트라이포드명"을 키로 저장
                resultMap.put(req.skillName() + "|" + req.tripodName(), synergies);
            }
        }

        // [4] 최종 결과 콘솔 출력 (사용자 요청사항)
        System.out.println("==== [Bulk Synergy Execution Result] ====");
        System.out.println("Total Requests: " + requests.size());
        System.out.println("Total Mapped Results: " + resultMap.size());
        resultMap.forEach((key, val) -> System.out.println("Matched -> " + key + " : " + val));

        return resultMap;
    }

    /**
     * 이름 세척: HTML 태그 제거 및 다양한 유니코드 공백 처리
     */
    private String sanitizeName(String raw) {
        if (raw == null) return "";
        return raw.replaceAll("<[^>]*>", "")
                .replaceAll("[\\u00A0\\u1680\\u180e\\u2000-\\u200a\\u2028\\u2029\\u202f\\u205f\\u3000]", " ")
                .trim();
    }
}