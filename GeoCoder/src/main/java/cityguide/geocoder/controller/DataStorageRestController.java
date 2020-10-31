package cityguide.geocoder.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cityguide.geocoder.config.RestServerConfig;
import cityguide.geocoder.dto.AddressDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class DataStorageRestController {
    private final RestServerConfig restServerConfig;
    private final RestTemplate restTemplate;

    public DataStorageRestController(RestTemplate restTemplate, RestServerConfig restServerConfig) {
        this.restTemplate = restTemplate;
        this.restServerConfig = restServerConfig;
    }

    public List<AddressDto> getAddresses() {
        log.debug("send get addresses");
        HttpHeaders headers = new HttpHeaders();

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getAddressesUri())
                .queryParam("hascoord", false);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<AddressDto[]> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                AddressDto[].class);
        log.debug("response {}", response);

        return (response.getBody() == null) ? new ArrayList<AddressDto>() : Arrays.asList(response.getBody());
    }

    public void sendAddress(AddressDto addressDto) {
        log.debug("send address with {}", addressDto);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getAddressesUri());

        HttpEntity<?> entity = new HttpEntity<>(addressDto);

        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
                String.class);
        log.debug("response {}", response);
    }

}
