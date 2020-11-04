package cityguide.geocoder.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cityguide.geocoder.config.RestServerConfig;
import cityguide.geocoder.dto.AddressDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public final class DataStorageRestControllerImpl implements DataStorageRestController<AddressDto> {
    private final RestServerConfig restServerConfig;
    private final RestTemplate restTemplate;

    public DataStorageRestControllerImpl(RestTemplate restTemplate, RestServerConfig restServerConfig) {
        this.restTemplate = restTemplate;
        this.restServerConfig = restServerConfig;
    }

    public List<AddressDto> getAddresses() {
        log.debug("send get addresses");

        final UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getAddressesUri())
                .queryParam("hascoord", false);

        final ResponseEntity<AddressDto[]> response;

        final HttpHeaders headers = new HttpHeaders();
        final HttpEntity<?> entity = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    entity, AddressDto[].class);
            log.debug("response {}", response);
        } catch (RestClientException e) {
            throw new GeoCoderRestControllerException("can't get addresses from data storage. Error " + e);
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new GeoCoderRestControllerException("response with error from data storage server " + response);
        }

        return (response.getBody() == null) ? new ArrayList<AddressDto>() : Arrays.asList(response.getBody());
    }

    public void sendAddress(AddressDto addressDto) {
        log.debug("send address with {}", addressDto);

        final UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(restServerConfig.getUrl() + restServerConfig.getAddressesUri());

        final HttpEntity<?> entity = new HttpEntity<>(addressDto);

        final ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity,
                    String.class);
            log.debug("response {}", response);
        } catch (RestClientException e) {
            throw new GeoCoderRestControllerException("can't send address to data storage. Error " + e);
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new GeoCoderRestControllerException("response with error from data storage server " + response);
        }
    }
}
