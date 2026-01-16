package com.jinouk.lostark.apiController;

import com.jinouk.lostark.service.charactersAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class charactersAPI {

    private final charactersAPIService service;


    @GetMapping("/siblings")
    public Mono<String> siblings(@RequestParam String name) { return service.getCharacterSiblings(name); }
}
