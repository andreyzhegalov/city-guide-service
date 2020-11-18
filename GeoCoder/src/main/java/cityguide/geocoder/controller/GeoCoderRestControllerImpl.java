package cityguide.geocoder.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import cityguide.geocoder.config.GeoCoderConfig;
import cityguide.geocoder.dto.Suggestion;
import cityguide.geocoder.dto.SuggestionsList;

@Controller
public final class GeoCoderRestControllerImpl implements GeoCoderRestController {
    private final RestTemplate restTemplate;
    private final GeoCoderConfig geoCoderConfig;

    public GeoCoderRestControllerImpl(RestTemplate restTemplate, GeoCoderConfig geoCoderConfig) {
        this.restTemplate = Objects.requireNonNull(restTemplate);
        this.geoCoderConfig = Objects.requireNonNull(geoCoderConfig);
    }

    public List<Suggestion> getSuggestions(String objectAddress) {
        final var personJsonObject = new JSONObject();
        personJsonObject.put("query", objectAddress);

        final HttpEntity<String> request = new HttpEntity<>(makeRequestBody(objectAddress), makeHeader());
        final SuggestionsList suggestions = restTemplate.postForObject(geoCoderConfig.getUrl(), request, SuggestionsList.class);
        assert suggestions != null;
        return suggestions.getSuggestions();
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
