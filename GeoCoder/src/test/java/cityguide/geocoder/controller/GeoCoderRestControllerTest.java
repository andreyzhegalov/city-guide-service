package cityguide.geocoder.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Collections;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import cityguide.geocoder.config.GeoCoderConfig;
import cityguide.geocoder.dto.SuggestionsList;

@ExtendWith(MockitoExtension.class)
class GeoCoderRestControllerTest {
    @Mock
    private RestTemplate restTemplate;
    private static GeoCoderConfig geoCoderConfig;
    private static final String URL = "http://test";
    private static final String TOKEN = "123";
    private static final String ADDRESS = "test address";

    @BeforeAll
    static void beforeAll() {
        geoCoderConfig = new GeoCoderConfig();
        geoCoderConfig.setUrl(URL);
        geoCoderConfig.setToken(TOKEN);
    }

    @Test
    void testSettingTokenToTheRequestHeader() {
        final var geoCoderRestController = new GeoCoderRestControllerImpl(restTemplate, geoCoderConfig);

        Mockito.when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(new SuggestionsList());

        geoCoderRestController.getSuggestions(ADDRESS);

        final var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        Mockito.verify(restTemplate).postForObject(anyString(), requestCaptor.capture(), eq(SuggestionsList.class));

        final HttpEntity<?> request = requestCaptor.getValue();
        assertThat(request.getHeaders().get("Authorization")).isEqualTo(Collections.singletonList("Token " + TOKEN));
    }

    @Test
    void testThatUrlFromConfig() {
        final var geoCoderRestController = new GeoCoderRestControllerImpl(restTemplate, geoCoderConfig);
        Mockito.when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(new SuggestionsList());
        geoCoderRestController.getSuggestions(ADDRESS);
        Mockito.verify(restTemplate).postForObject(eq(URL), any(), eq(SuggestionsList.class));
    }

    @Test
    void testThatSetCorrectAddressInTheRequest() {
        final var geoCoderRestController = new GeoCoderRestControllerImpl(restTemplate, geoCoderConfig);
        Mockito.when(restTemplate.postForObject(anyString(), any(), any())).thenReturn(new SuggestionsList());
        geoCoderRestController.getSuggestions(ADDRESS);
        final var requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        Mockito.verify(restTemplate).postForObject(anyString(), requestCaptor.capture(), eq(SuggestionsList.class));

        final HttpEntity<?> request = requestCaptor.getValue();
        assertThat(request.getBody()).isEqualTo("{\"query\":\"" + ADDRESS + "\"}");
    }
}
