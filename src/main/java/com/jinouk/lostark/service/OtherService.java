package com.jinouk.lostark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class OtherService {

    // WebClient는 설정 클래스(Config)에서 빈으로 등록되어 있어야 합니다.
    private final WebClient loawebclient;

    /**
     * 캐릭터 프로필 정보 조회 (치명, 특화, 신속 등)
     * 호출 API: /armories/characters/{name}/profiles
     */
    public Mono<String> getArmoriesCharacterProfile(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/profiles", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * 캐릭터의 장비 정보 조회 (무기 레벨 추출용)
     * 호출 API: /armories/characters/{name}/equipment
     */
    public Mono<String> getArmoriesCharacterEquipment(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/equipment", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    /**
     * 캐릭터의 아크 패시브 정보 조회 (Title 추출용)
     * 호출 API: /armories/characters/{name}/arkpassive
     */
    public Mono<String> getArmoriesCharacterArkpassive(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/arkpassive", name)
                .retrieve()
                .bodyToMono(String.class);
    }
}