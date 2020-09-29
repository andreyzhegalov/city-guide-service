package cityguide.geocoder.controller;

import java.util.Arrays;
import java.util.List;

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

import cityguide.geocoder.config.RestServerConfig;
import cityguide.geocoder.dto.AddressDto;

@Controller
public class DataStorageRestController {
    @Autowired
    private RestServerConfig restServerConfig;

    private static final Logger logger = LoggerFactory.getLogger(DataStorageRestController.class);
    private final RestTemplate restTemplate;

    public DataStorageRestController() {
        this.restTemplate = new RestTemplate();
    }

    public List<AddressDto> getAddresses() {
        logger.debug("send get adresses");
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getAddressesUri())
                .queryParam("hascoord", false);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<AddressDto[]> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                AddressDto[].class);
        logger.debug("response {}", response);
        return Arrays.asList(response.getBody());
    }

    public void sendAddress(AddressDto addressDto) {
        logger.debug("send address with {}", addressDto);
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getAddressesUri());

        HttpEntity<?> entity = new HttpEntity<>(addressDto, headers);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
                String.class);
        logger.debug("response {}", response);
    }

}
