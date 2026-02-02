package com.jinouk.lostark.simulator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SynergyTypes")
@Getter
@Setter
@NoArgsConstructor
public class SynergyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "synergy_id")
    private Integer synergyId;

    @Column(name = "description", nullable = false, unique = true)
    private String description;

    // 양방향 매핑이 필요한 경우 추가 (선택사항)
    @ManyToMany(mappedBy = "synergies")
    private List<SkillDetail> skills = new ArrayList<>();
}
