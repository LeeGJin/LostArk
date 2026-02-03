package com.jinouk.lostark.apiController;

import com.jinouk.lostark.apiParse.apiParseService.characterProfileParseService;
import com.jinouk.lostark.apiParse.apiParseService.equipmentWeaponParseService;
import com.jinouk.lostark.service.armoriesAPIService;
import com.jinouk.lostark.simulator.dto.skillsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class armoriesAPI {
    private final armoriesAPIService service;
    private final characterProfileParseService parseProfile;
    private final equipmentWeaponParseService parseWeapon;

    @GetMapping("/character")
    public Mono<String> character(@RequestParam String name) {
        return service.getArmoriesCharacter(name);
    }

    @GetMapping("/stat")
    public Mono<String> stat(@RequestParam String name) {
        return service.getArmoriesCharacterProfile(name);
    }

    @GetMapping("/equipment")
    public Mono<?> equipment(@RequestParam String name) {
        return service.getArmoriesCharacterEquipment(name);
    }

    @GetMapping("/avatars")
    public Mono<String> avatars(@RequestParam String name) { return service.getArmoriesCharacterAvatars(name);}

    @GetMapping("/combat-skills")
    public Mono<List<skillsResponse>> combat_skills(@RequestParam String name) { return service.getArmoriesCharacterCombatSkills(name);}

    @GetMapping("/engravings")
    public Mono<String> engravings(@RequestParam String name) { return service.getArmoriesCharacterEngravings(name);}

    @GetMapping("/cards")
    public Mono<String> cards(@RequestParam String name) { return service.getArmoriesCharacterCards(name);}

    @GetMapping("/gems")
    public Mono<String> gems(@RequestParam String name) { return service.getArmoriesCharacterGems(name);}

    @GetMapping("/colosseums")
    public Mono<String> colosseums(@RequestParam String name) { return service.getArmoriesCharacterColosseums(name);}

    @GetMapping("/collectibles")
    public Mono<String> collectibles(@RequestParam String name) { return service.getArmoriesCharacterCollectibles(name);}

    @GetMapping("/arkpassive")
    public Mono<String> arkpassive(@RequestParam String name) { return service.getArmoriesCharacterArkpassive(name);}

    @GetMapping("/arkgrid")
    public Mono<String> arkgrid(@RequestParam String name) { return service.getArmoriesCharacterArkgrid(name);}

}
