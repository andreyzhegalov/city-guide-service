package cityguide.telegrambot.controller;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cityguide.telegrambot.config.RestServerConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataStorageRestControllerImpl implements DataStorageRestController {
    private final RestServerConfig restServerConfig;
    private final RestTemplate restTemplate;

    public DataStorageRestControllerImpl(RestTemplate restTemplate, RestServerConfig restServerConfig) {
        this.restTemplate = restTemplate;
        this.restServerConfig = restServerConfig;
    }

    @Override
    public Optional<String> getShowPlaceDescription(float latitude, float longitude, int searchRadius) {
        log.info("invoked sendGet lat {} lon {} searchRadius {}", latitude, longitude, searchRadius);
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getGetShowplacesUri())
                .queryParam("lat", latitude).queryParam("lon", longitude).queryParam("radius", searchRadius);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                String.class);

        log.info("response {}", response);
        return Optional.ofNullable(response.getBody());
    }
}
