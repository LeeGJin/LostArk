package com.jinouk.lostark.repository;

import com.jinouk.lostark.entity.characterEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface rankingRepository extends JpaRepository<characterEntity, String> {

    // 1. [조회] 아이템 레벨/전투력 순으로 랭킹 목록 가져오기
    // 페이징(Pageable)을 사용하여 상위 유저들을 끊어서 조회합니다.
    List<characterEntity> findAllByOrderByItemLevelDesc(Pageable pageable);
    List<characterEntity> findAllByOrderByCombatPowerDesc(Pageable pageable);

    // [조회] 특정 직업별 전투력 순위 조회
    List<characterEntity> findAllByCharacterClassOrderByCombatPowerDesc(String characterClass, Pageable pageable);

    // [추가 가능] 아크 패시브 포인트나 특정 스탯 기준 랭킹이 필요할 경우 추가
    //List<characterEntity> findAllByArkPassiveOrderByCombatPowerDesc(String characterClass, Pageable pageable);
    List<characterEntity> findByCharacterClassAndArkPassiveOrderByCombatPowerDesc(
            String characterClass,
            String arkPassive,
            Pageable pageable
    );
    // 2. [검색/수정] 닉네임으로 기존 유저 존재 여부 확인
    // Optional을 사용하여 null 체크를 안전하게 처리합니다.
    Optional<characterEntity> findByName(String name);

    // 3. [관리] Top 1000 유지를 위한 최하위 유저 찾기
    // 아이템 레벨이 가장 낮은(Asc) 유저를 찾아 신규 진입자와 교체하기 위함입니다.
    characterEntity findFirstByOrderByItemLevelAsc();
}
