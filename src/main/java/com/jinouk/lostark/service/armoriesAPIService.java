package com.jinouk.lostark.service;

import com.jinouk.lostark.simulator.dto.skillsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class armoriesAPIService {

    //캐릭터 전체 정보 조희
    public Mono<String> getArmoriesCharacter(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    private final WebClient loawebclient;
    //치명 , 특화 , 신속 , 제압 , 인내 , 숙련 등의 수치및 증감량 조회
    public Mono<String> getArmoriesCharacterProfile(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/profiles", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //사용자 캐릭터가 착용한 장비 조회
    public Mono<String> getArmoriesCharacterEquipment(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/equipment", name)
                .retrieve()
                .bodyToMono(String.class);

    }

    //사용자 캐릭터가 착용한 아바타 장비 조회
    public Mono<String> getArmoriesCharacterAvatars(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/avatars", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 스킬과 스킬에 장착한 룬 정보 조회
    public Mono<List<skillsResponse>> getArmoriesCharacterCombatSkills(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/combat-skills", name)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<skillsResponse>>() {});

    }

    //캐릭터의 각인 정보 조회
    public Mono<String> getArmoriesCharacterEngravings(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/engravings", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 카드 정보 조회
    public Mono<String> getArmoriesCharacterCards(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/cards", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 보석 정보 조회
    public Mono<String> getArmoriesCharacterGems(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/gems", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 콜로세움(PVP) 정보 조회
    public Mono<String> getArmoriesCharacterColosseums(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/colosseums", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 내실 정보 조회
    public Mono<String> getArmoriesCharacterCollectibles(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/collectibles", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 아크 패시브 정보 조회
    public Mono<String> getArmoriesCharacterArkpassive(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/arkpassive", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 아크 그리드 코어 정보 조회
    public Mono<String> getArmoriesCharacterArkgrid(String name) {
        return loawebclient
                .get()
                .uri("/armories/characters/{name}/arkgrid", name)
                .retrieve()
                .bodyToMono(String.class);
    }
}
