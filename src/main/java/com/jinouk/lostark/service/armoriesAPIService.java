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
}
