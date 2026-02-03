package com.jinouk.lostark.repository;

import com.jinouk.lostark.entity.characterEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface rankingRepository extends JpaRepository<characterEntity, String> {

    // 1. 기존 조회 기능 (유지)
    List<characterEntity> findAllByOrderByItemLevelDesc(Pageable pageable);
    List<characterEntity> findAllByCharacterClassOrderByCombatPowerDesc(String characterClass, Pageable pageable);
    List<characterEntity> findAllByOrderByCombatPowerDesc(Pageable pageable);

    // 2. 검색 및 업데이트를 위한 기능 추가
    // 닉네임으로 유저가 있는지 확인 (PK가 name이므로 사실 findById와 동일하지만 명시적으로 작성)
    Optional<characterEntity> findByName(String name);

    // 3. Top 1000 관리를 위한 기능 추가
    // 전투력 기준 오름차순(Asc)으로 정렬했을 때 가장 첫 번째 유저 = 현재 DB 내 최하위 전투력 유저
    characterEntity findFirstByOrderByCombatPowerAsc();
}
