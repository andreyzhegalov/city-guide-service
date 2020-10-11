package cityguide.datacollector.controller;

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
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ShowPlaceCollectorRestController implements ShowPlaceSendController {
    @Autowired
    private RestServerConfig restServerConfig;
    private final RestTemplate restTemplate;

    public ShowPlaceCollectorRestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void send(ShowPlaceDto showPlace) {
        log.info("invoked sendPost with {}", showPlace);
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getShowplacesUri())
                .queryParam("address", showPlace.getAddress());

        HttpEntity<?> entity = new HttpEntity<>(showPlace, headers);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
                String.class);
        log.info("response {}", response);
    }

}
