package com.jinouk.lostark.simulator.controller;

import com.jinouk.lostark.simulator.dto.SynergyRequest;
import com.jinouk.lostark.simulator.service.SynergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(
        origins = {"http://localhost:3000", "http://localhost:5173" , "http://localhost:8080"},
        allowCredentials = "true"
)
public class SynergyController {

    private final SynergyService synergyService;

    @PostMapping("/bulk")
    public ResponseEntity<Map<String, List<String>>> checkBulkSynergy(@RequestBody List<SynergyRequest> requests) {
        return ResponseEntity.ok(synergyService.getBulkSynergies(requests));
    }
}