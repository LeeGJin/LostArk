package com.jinouk.lostark.controller;

import com.jinouk.lostark.service.armoriesAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class armoriesAPI {
    private final armoriesAPIService service;

    @GetMapping("/stat")
    public Mono<String> stat() {
        return service.getArmoriesCharacterProfile();
    }

    @GetMapping("/equipment")
    public Mono<String> equipment() {
        return service.getArmoriesCharacterEquipment();
    }

    @GetMapping("/avatars")
    public Mono<String> avatars() { return service.getArmoriesCharacterAvatars();}

    @GetMapping("/combat-skills")
    public Mono<String> combat_skills() { return service.getArmoriesCharacterCombatSkills();}

    @GetMapping("/engravings")
    public Mono<String> engravings() { return service.getArmoriesCharacterEngravings();}

    @GetMapping("/cards")
    public Mono<String> cards() { return service.getArmoriesCharacterCards();}

    @GetMapping("/gems")
    public Mono<String> gems() { return service.getArmoriesCharacterGems();}

    @GetMapping("/colosseums")
    public Mono<String> colosseums() { return service.getArmoriesCharacterColosseums();}

    @GetMapping("/collectibles")
    public Mono<String> collectibles() { return service.getArmoriesCharacterCollectibles();}

    @GetMapping("/arkpassive")
    public Mono<String> arkpassive() { return service.getArmoriesCharacterArkpassive();}

    @GetMapping("/arkgrid")
    public Mono<String> arkgrid() { return service.getArmoriesCharacterArkgrid();}

}
