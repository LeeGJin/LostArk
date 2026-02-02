package com.jinouk.lostark.simulator.repository;

import com.jinouk.lostark.simulator.entity.SkillDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SkillDetailRepository extends JpaRepository<SkillDetail, Long> {

    // API 데이터(스킬명, 트포명)와 DB를 대조하는 메서드
    List<SkillDetail> findBySkillNameAndTripodName(String skillName, String tripodName);

    // ✅ @EntityGraph 추가: 시너지(synergies) 데이터를 JOIN으로 한 번에 가져와서 N+1 문제를 방지합니다.
    @EntityGraph(attributePaths = {"synergies"})
    List<SkillDetail> findBySkillNameIn(List<String> skillNames);
}