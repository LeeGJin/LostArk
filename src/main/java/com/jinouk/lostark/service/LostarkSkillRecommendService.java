package com.jinouk.lostark.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import java.time.Duration;

@Service
public class LostarkSkillRecommendService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LostarkSkillRecommendService(WebClient.Builder builder) {

        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(c -> c.defaultCodecs().maxInMemorySize(4 * 1024 * 1024))
                .build();

        this.webClient = builder
                .baseUrl("https://lostark.game.onstove.com")
                .exchangeStrategies(strategies)
                .defaultHeader(HttpHeaders.USER_AGENT,
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120 Safari/537.36")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json, text/plain, */*")
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, "ko-KR,ko;q=0.9,en;q=0.7")
                .build();
    }

    /** 스킬 코드 문자열 (예: 8AD911FC4F26A5CB...) */
    public Mono<String> fetchSkillCode(String memberNo, String worldNo, String pcId) {
        return webClient.post()
                .uri("/Profile/SkillRecommend")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)

                // ✅ 서버가 AJAX 요청처럼 보이길 요구하는 케이스가 많아서 헤더 보강
                .header("X-Requested-With", "XMLHttpRequest")
                .header(HttpHeaders.ORIGIN, "https://lostark.game.onstove.com")
                .header(HttpHeaders.REFERER, "https://lostark.game.onstove.com/Profile/")

                .body(BodyInserters.fromFormData("memberNo", memberNo)
                        .with("worldNo", worldNo)
                        .with("pcId", pcId))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .map(this::extractSkillCodeFromResponse);
    }

    private String extractSkillCodeFromResponse(String responseJson) {
        try {
            JsonNode root = objectMapper.readTree(responseJson);

            int code = root.path("code").asInt(-9999);
            if (code != 0) {
                // ✅ 원인 파악을 위해 서버가 준 정보를 같이 보여주기
                String title = root.path("title").asText("");
                String content = root.path("content").asText("");

                // content가 길 수 있으니 너무 길면 잘라서 던져도 됨
                String shortContent = content.length() > 500 ? content.substring(0, 500) + "..." : content;

                throw new IllegalStateException(
                        "SkillRecommend 실패: code=" + code + ", title=" + title + ", content=" + shortContent
                );
            }

            String contentHtml = root.path("content").asText(null);
            if (contentHtml == null || contentHtml.isBlank()) {
                throw new IllegalStateException("응답 content가 비어있습니다.");
            }

            Document doc = Jsoup.parse(contentHtml);
            Elements codeDiv = doc.select("div.code");

            if (codeDiv.isEmpty()) {
                throw new IllegalStateException("스킬 코드(div.code)를 찾지 못했습니다. content=" + contentHtml);
            }

            return codeDiv.first().text().trim();
        } catch (Exception e) {
            throw new RuntimeException("스킬 코드 파싱 실패: responseJson=" + safeCut(responseJson), e);
        }
    }

    private static String safeCut(String s) {
        if (s == null) return "null";
        return s.length() > 800 ? s.substring(0, 800) + "..." : s;
    }
}