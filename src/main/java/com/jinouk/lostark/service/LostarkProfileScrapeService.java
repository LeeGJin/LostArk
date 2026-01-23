package com.jinouk.lostark.service;

import com.jinouk.lostark.dto.LostarkProfileIds;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LostarkProfileScrapeService {

    private static final Pattern MEMBER_NO = Pattern.compile(
            "var\\s+_memberNo\\s*=\\s*'([^']*)'\\s*;"
    );
    private static final Pattern WORLD_NO = Pattern.compile(
            "var\\s+_worldNo\\s*=\\s*'([^']*)'\\s*;"
    );
    private static final Pattern PC_ID = Pattern.compile(
            "var\\s+_pcId\\s*=\\s*'([^']*)'\\s*;"
    );

    private final WebClient webClient;

    public LostarkProfileScrapeService(WebClient.Builder builder) {

        // ✅ 응답 HTML이 커도 String으로 받을 수 있게 제한을 늘림 (예: 4MB)
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer ->
                        configurer.defaultCodecs().maxInMemorySize(4 * 1024 * 1024)
                )
                .build();

        this.webClient = builder
                .baseUrl("https://lostark.game.onstove.com")
                .exchangeStrategies(strategies)
                .defaultHeader(HttpHeaders.USER_AGENT,
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120 Safari/537.36")
                .defaultHeader(HttpHeaders.ACCEPT,
                        "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, "ko-KR,ko;q=0.9,en;q=0.7")
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br")
                .build();
    }

    public Mono<LostarkProfileIds> fetchIdsByNickname(String nickname) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Profile/Character/{name}")
                        .build(nickname))
                .accept(MediaType.TEXT_HTML)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .map(this::extractIdsFromHtml);
    }

    private LostarkProfileIds extractIdsFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        String full = doc.outerHtml();

        String memberNo = findFirstGroupOrThrow(MEMBER_NO, full, "_memberNo");
        String worldNo = findFirstGroupOrThrow(WORLD_NO, full, "_worldNo");
        String pcId = findFirstGroupOrThrow(PC_ID, full, "_pcId");

        return new LostarkProfileIds(memberNo, worldNo, pcId);
    }

    private static String findFirstGroupOrThrow(Pattern p, String s, String keyName) {
        Matcher m = p.matcher(s);
        if (m.find()) return m.group(1);
        throw new IllegalStateException(
                "HTML에서 " + keyName + " 값을 찾지 못했습니다. (차단 페이지 또는 구조 변경 가능)"
        );
    }
}
