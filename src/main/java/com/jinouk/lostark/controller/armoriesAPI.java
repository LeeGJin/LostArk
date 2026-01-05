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
}
