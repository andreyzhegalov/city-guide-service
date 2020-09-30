package cityguide.datacollector.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cityguide.datacollector.config.RestServerConfig;
import cityguide.datacollector.dto.ShowPlaceDto;

@Controller
public class ShowPlaceCollectorRestController {
    @Autowired
    private RestServerConfig restServerConfig;

    private static final Logger logger = LoggerFactory.getLogger(ShowPlaceCollectorRestController.class);
    private final RestTemplate restTemplate;

    public ShowPlaceCollectorRestController() {
        this.restTemplate = new RestTemplate();
    }

    public void sendPost(ShowPlaceDto showPlace) {
        logger.info("invoked sendPost with {}", showPlace);
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getShowplacesUri())
                .queryParam("address", showPlace.getAddress());

        HttpEntity<?> entity = new HttpEntity<>(showPlace, headers);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
                String.class);
        logger.info("response {}", response);
    }

}
