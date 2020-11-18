package cityguide.telegrambot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import cityguide.telegrambot.config.RestServerConfig;

@ExtendWith(MockitoExtension.class)
class DataStorageRestControllerImplTest {
    @Mock
    private RestTemplate restTemplate;
    private static RestServerConfig restServerConfig;
    private final static float LATITUDE = 10.0F;
    private final static float LONGITUDE = 20.0F;
    private final static int RADIUS = 100;
    private final static String RESULT = "description";

    @BeforeAll
    static void setUp() {
        restServerConfig = new RestServerConfig();
        restServerConfig.setUrl("http://test.com/");
        restServerConfig.setShowplacesUri("showplaces");
    }

    @Test
    void checkUrlFormat() {
        final var dataStorageRestController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);
        final var response = new ResponseEntity<String>(RESULT, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(response);

        dataStorageRestController.getShowPlaceDescription(LATITUDE, LONGITUDE, RADIUS);

        final var requestCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(restTemplate).exchange(requestCaptor.capture(), eq(HttpMethod.GET), any(), eq(String.class));

        final String resultUrl = restServerConfig.getUrl() + restServerConfig.getShowplacesUri() + "?lat="
                + String.valueOf(LATITUDE) + "&lon=" + String.valueOf(LONGITUDE) + "&radius=" + String.valueOf(RADIUS);
        assertThat(requestCaptor.getValue()).isEqualTo(resultUrl);
    }

    @Test
    void getShowPlaceDescription() {
        final var dataStorageRestController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);

        final ResponseEntity<String> response = new ResponseEntity<>(RESULT, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(response);

        final var result = dataStorageRestController.getShowPlaceDescription(LATITUDE, LONGITUDE, RADIUS);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(RESULT);
    }

    @Test
    void shouldThrowExceptionWhenServerResponseNotOk() {
        final var dataStorageRestController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);

        final ResponseEntity<String> response = new ResponseEntity<>(RESULT, HttpStatus.BAD_REQUEST);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(response);

        assertThatThrownBy(() -> dataStorageRestController.getShowPlaceDescription(LATITUDE, LONGITUDE, RADIUS))
                .isInstanceOf(TelegramBotControllerException.class);
    }

    @Test
    void shouldThrowExceptionThenServerConnectionRefused() {
        final var dataStorageRestController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);

        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenThrow(RestClientException.class);

        assertThatThrownBy(() -> dataStorageRestController.getShowPlaceDescription(LATITUDE, LONGITUDE, RADIUS))
                .isInstanceOf(TelegramBotControllerException.class);
    }
}
