package GeoCoder;

import java.util.Collections;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import GeoCoder.dto.SuggestionsList;

public class RestController {
    private final RestTemplate restTemplate;
    private final static String URL = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";
    private final String token;

    public RestController(String token) {
        this.restTemplate = new RestTemplate();
        this.token = token;
    }

    public SuggestionsList doPost(String objectAddress) {
        final var personJsonObject = new JSONObject();
        personJsonObject.put("query", objectAddress);

        final HttpEntity<String> request = new HttpEntity<String>(makeRequestBody(objectAddress), makeHeader());
        final SuggestionsList response = restTemplate.postForObject(URL, request, SuggestionsList.class);
        return response;
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
