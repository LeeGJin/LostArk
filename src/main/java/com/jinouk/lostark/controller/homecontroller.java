package com.jinouk.lostark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class homecontroller {

    @GetMapping("/")
    public String home() {
        return "index.html";
    }
}
