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
import cityguide.datacollector.dto.AddressData;

@Controller
public class DataCollectorRestController {
    @Autowired
    private RestServerConfig restServerConfig;

    private static final Logger logger = LoggerFactory.getLogger(DataCollectorRestController.class);
    private final RestTemplate restTemplate;

    public DataCollectorRestController() {
        this.restTemplate = new RestTemplate();
    }

    public void sendPost(AddressData adressData) {
        logger.info("invoked sendPost with {}", adressData);
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getShowplacesUri())
                .queryParam("address", adressData.getAddress());

        HttpEntity<?> entity = new HttpEntity<>(adressData, headers);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
                String.class);
        logger.info("response {}", response);
    }

}
