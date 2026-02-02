package com.jinouk.lostark.simulator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SkillDetails")
@Getter @Setter
@NoArgsConstructor
public class SkillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_detail_id")
    private Integer skillDetailId;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @Column(name = "tripod_name", nullable = false)
    private String tripodName;

    @ManyToMany
    @JoinTable(
            name = "SkillSynergyMapping", // 이전에 만든 매핑 테이블 이름
            joinColumns = @JoinColumn(name = "skill_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "synergy_id")
    )
    private List<SynergyType> synergies = new ArrayList<>();
}
