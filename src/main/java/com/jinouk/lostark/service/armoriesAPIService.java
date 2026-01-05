package com.jinouk.lostark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class armoriesAPIService {

    private final WebClient lostArkWebClient;

    //치명 , 특화 , 신속 , 제압 , 인내 , 숙련등의 수치및 증감량 조회
    public Mono<String> getArmoriesCharacterProfile() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/profiles", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //사용자 캐릭터가 착용한 장비 조회
    public Mono<String> getArmoriesCharacterEquipment() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/equipment", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //사용자 캐릭터가 착용한 아바타 장비 조회
    public Mono<String> getArmoriesCharacterAvatars() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/avatars", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 스킬과 스킬에 장착한 룬 정보 조회
    public Mono<String> getArmoriesCharacterCombatSkills() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/combat-skills", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 각인 정보 조회
    public Mono<String> getArmoriesCharacterEngravings() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/engravings", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 카드 정보 조회
    public Mono<String> getArmoriesCharacterCards() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/cards", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 보석 정보 조회
    public Mono<String> getArmoriesCharacterGems() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/gems", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 콜로세움(PVP) 정보 조회
    public Mono<String> getArmoriesCharacterColosseums() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/colosseums", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 내실 정보 조회
    public Mono<String> getArmoriesCharacterCollectibles() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/collectibles", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 아크 패시브 정보 조회
    public Mono<String> getArmoriesCharacterArkpassive() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/arkpassive", name)
                .retrieve()
                .bodyToMono(String.class);
    }

    //캐릭터의 아크 그리드 코어 정보 조회
    public Mono<String> getArmoriesCharacterArkgrid() {
        //이름은 나중에 입력값으로 바꿀 것.
        String name = "치킨버거사주세요";
        return lostArkWebClient
                .get()
                .uri("/armories/characters/{name}/arkgrid", name)
                .retrieve()
                .bodyToMono(String.class);
    }
}
