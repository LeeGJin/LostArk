package com.jinouk.lostark.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class marketsAPIService {

    private final WebClient loawebclient;

    public Mono<String> getMarketData(String itemName, int categoryCode, String grade) {
        return Flux.range(1, 5)
                .concatMap(pageNo -> {
                    Map<String, Object> requestBody = new HashMap<>();
                    requestBody.put("Sort", "CURRENT_MIN_PRICE");
                    requestBody.put("CategoryCode", categoryCode);
                    requestBody.put("ItemTier", 0);
                    requestBody.put("ItemGrade", grade);
                    requestBody.put("ItemName", itemName);
                    requestBody.put("PageNo", pageNo);
                    requestBody.put("SortCondition", "DESC");

                    return loawebclient
                            .post()
                            .uri("/markets/items")
                            .header("Content-Type", "application/json")
                            .bodyValue(requestBody)
                            .retrieve()
                            .bodyToMono(String.class);
                })
                .collectList()
                .map(results -> "[" + String.join(",", results) + "]");
    }
}
