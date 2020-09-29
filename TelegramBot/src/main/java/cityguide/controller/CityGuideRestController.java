package cityguide.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cityguide.config.RestServerConfig;

@Component
public class CityGuideRestController {
    @Autowired private RestServerConfig restServerConfig;

    private static final Logger logger = LoggerFactory.getLogger(CityGuideRestController.class);
    private final RestTemplate restTemplate;

    public CityGuideRestController() {
        this.restTemplate = new RestTemplate();
    }

    public Optional<String> sendGet(Float latitude, Float longitude, int searchRadius) {
        logger.info("invoked sendGet lat {} lon {} searchRadius {}", latitude, longitude, searchRadius);
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getGetShowplacesUri())
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("radius", searchRadius);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                String.class);

        logger.info("response {}", response);
        return Optional.of(response.getBody());
    }
}
