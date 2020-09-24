package cityguide.geocoder;

import java.util.Collections;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import cityguide.geocoder.dto.SuggestionsList;

public class GeoCoder {
    private final RestTemplate restTemplate;
    private final static String URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";
    private final String token;

    public GeoCoder(String token) {
        this.restTemplate = new RestTemplate();
        this.token = token;
    }

    public SuggestionsList getSuggestions(String objectAddress) {
        final var personJsonObject = new JSONObject();
        personJsonObject.put("query", objectAddress);

        final HttpEntity<String> request = new HttpEntity<>(makeRequestBody(objectAddress), makeHeader());
        return restTemplate.postForObject(URL, request, SuggestionsList.class);
    }

    private HttpHeaders makeHeader() {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Token " + this.token);
        return headers;
    }

    private String makeRequestBody(String objectAddress) {
        final var personJsonObject = new JSONObject();
        personJsonObject.put("query", objectAddress);
        return personJsonObject.toString();
    }
}
