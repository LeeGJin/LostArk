package com.jinouk.lostark.webClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LlostArkWebClient {

    @Value("${ApiKey}")
    private String ApiKey;

    @Bean
    public WebClient lostArkWebClient() {
        return WebClient.builder()
                .baseUrl("https://developer-lostark.game.onstove.com")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1))
                .defaultHeader(HttpHeaders.AUTHORIZATION, ApiKey)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
