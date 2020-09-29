package cityguide.geocoder.controller;

import java.util.Collections;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import cityguide.geocoder.config.GeoCoderConfig;
import cityguide.geocoder.dto.SuggestionsList;

@Controller
public class GeoCoderRestController {
    private final RestTemplate restTemplate;

    @Autowired
    private GeoCoderConfig geoCoderConfig;

    public GeoCoderRestController() {
        this.restTemplate = new RestTemplate();
    }

    public SuggestionsList getSuggestions(String objectAddress) {
        final var personJsonObject = new JSONObject();
        personJsonObject.put("query", objectAddress);

        final HttpEntity<String> request = new HttpEntity<>(makeRequestBody(objectAddress), makeHeader());
        return restTemplate.postForObject(geoCoderConfig.getUrl(), request, SuggestionsList.class);
    }

    private HttpHeaders makeHeader() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + geoCoderConfig.getToken());
        return headers;
    }

    private String makeRequestBody(String objectAddress) {
        final var personJsonObject = new JSONObject();
        personJsonObject.put("query", objectAddress);
        return personJsonObject.toString();
    }
}
