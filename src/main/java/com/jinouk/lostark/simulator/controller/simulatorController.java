package com.jinouk.lostark.simulator.controller;


import com.jinouk.lostark.simulator.postProcess.skillPostProcess;
import com.jinouk.lostark.simulator.service.simulatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:5173" , "http://localhost:8080"},
        allowCredentials = "true"
)
public class simulatorController {

    private final simulatorService service;

    @GetMapping("/do")
    public Mono<List<skillPostProcess>> doProcess(@RequestParam String characterName) {
        return service.doProcess(characterName);
    }

}
