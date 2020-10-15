package cityguide.datacollector.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cityguide.datacollector.config.RestServerConfig;
import cityguide.datacollector.dto.ShowPlaceDto;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ShowPlaceCollectorRestController implements ShowPlaceSendController {
    private final RestServerConfig restServerConfig;
    private final RestTemplate restTemplate;

    public ShowPlaceCollectorRestController(RestTemplate restTemplate, RestServerConfig restServerConfig) {
        this.restTemplate = restTemplate;
        this.restServerConfig = restServerConfig;
    }

    @Override
    public void send(ShowPlaceDto showPlace) {
        log.debug("invoked sendPost with {}", showPlace);
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getShowplacesUri())
                .queryParam("address", showPlace.getAddress());

        HttpEntity<?> entity = new HttpEntity<>(showPlace, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
                    String.class);
            log.debug("response {}", response);
        } catch (ResourceAccessException e) {
            log.error("can't send showplace to datastorage server");
        }
    }
}
