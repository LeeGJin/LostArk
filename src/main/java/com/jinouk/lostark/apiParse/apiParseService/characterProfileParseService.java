package com.jinouk.lostark.apiParse.apiParseService;
import com.jinouk.lostark.apiParse.parseDto.characterProfileDto;
import com.jinouk.lostark.service.armoriesAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class characterProfileParseService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient loawebclient;

    public Mono<String> getAndProcessProfile(String characterName) {

        armoriesAPIService armoriesAPIService = new armoriesAPIService(loawebclient);

        return armoriesAPIService.getArmoriesCharacterProfile(characterName)
                .doOnNext(jsonString -> {
                    try {

                        JsonNode root = objectMapper.readTree(jsonString);
                        JsonNode statsArray = root.path("Stats");

                        if (statsArray.isArray() && !statsArray.isEmpty()) {
                            //치명 , 특화 , 신속 , 제압 , 인내 , 숙련
                            int critical = statsArray.get(0).path("Value").asInt();
                            int Specialization = statsArray.get(1).path("Value").asInt();
                            int Swiftness = statsArray.get(2).path("Value").asInt();
                            int Domination = statsArray.get(3).path("Value").asInt();
                            int Endurance = statsArray.get(4).path("Value").asInt();
                            int Expertise = statsArray.get(5).path("Value").asInt();
                            characterProfileDto dto = new characterProfileDto(critical, Specialization, Swiftness, Domination, Endurance, Expertise );
                            System.out.println("--캐릭터 전처리 결과--");
                            System.out.println("치명: " + dto.getCritical());
                            System.out.println("특화: " + dto.getSpecialization());
                            System.out.println("신속: " + dto.getSwiftness());
                            System.out.println("제압: " + dto.getDomination());
                            System.out.println("인내: " + dto.getEndurance());
                            System.out.println("숙력: " + dto.getExpertise());
                        }
                    } catch (Exception e) {
                        System.err.println("JSON 가공 중 오류: " + e.getMessage());
                    }
                });
    }
}
