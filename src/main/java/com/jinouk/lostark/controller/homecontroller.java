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

    @GetMapping("/profilePage")
    public String profile(){
        return "forward:/index.html";
    }
    @GetMapping("/raidPage")
    public String raid(){
        return "forward:/index.html";
    }
    @GetMapping("/simulatorPage")
    public String simulator(){
        return "forward:/index.html";
    }
    @GetMapping("/auctionPage")
    public String auction(){
        return "forward:/index.html";
    }
}
