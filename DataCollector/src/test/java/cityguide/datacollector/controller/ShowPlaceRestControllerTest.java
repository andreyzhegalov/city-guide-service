package cityguide.datacollector.controller;

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

import cityguide.datacollector.config.RestServerConfig;
import cityguide.datacollector.dto.ShowPlaceDto;

@ExtendWith(MockitoExtension.class)
class ShowPlaceCollectorRestControllerTest {
    private final static String RESULT = "description";

    @Mock
    private RestTemplate restTemplate;

    private static RestServerConfig restServerConfig;

    @BeforeAll
    static void setUp() {
        restServerConfig = new RestServerConfig();
        restServerConfig.setUrl("http://test.com/");
        restServerConfig.setShowplacesUri("showplaces");
    }

    @Test
    void testCorrectUrl() {
        final var address = "address";
        final var testShowPlace = new ShowPlaceDto();
        testShowPlace.setInfo("info");
        testShowPlace.setAddress(address);

        final var response = new ResponseEntity<String>(RESULT, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(response);

        new ShowPlaceCollectorRestController(restTemplate, restServerConfig).send(testShowPlace);

        final var requestCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(restTemplate).exchange(requestCaptor.capture(), eq(HttpMethod.POST), any(), eq(String.class));

        final String resultUrl = restServerConfig.getUrl() + restServerConfig.getShowplacesUri() + "?address="
                + address;
        assertThat(requestCaptor.getValue()).isEqualTo(resultUrl);
    }

    @Test
    void shouldThrowExceptionWhenServerResponseNotOk() {
        final var showPlaceCollectorRestController = new ShowPlaceCollectorRestController(restTemplate,
                restServerConfig);

        final ResponseEntity<String> response = new ResponseEntity<>(RESULT, HttpStatus.BAD_REQUEST);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(response);

        assertThatThrownBy(() -> showPlaceCollectorRestController.send(new ShowPlaceDto()))
                .isInstanceOf(DataCollectorRestControllerException.class);
    }

    @Test
    void shouldThrowExceptionThenServerConnectionRefused() {
        final var dataStorageRestController = new ShowPlaceCollectorRestController(restTemplate, restServerConfig);

        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenThrow(RestClientException.class);

        assertThatThrownBy(() -> dataStorageRestController.send(new ShowPlaceDto()))
                .isInstanceOf(DataCollectorRestControllerException.class);
    }

    @Test
    void sendPostTest() {
        final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);

        final RestServerConfig restServerConfig = new RestServerConfig();
        restServerConfig.setUrl("http://test.com/");
        restServerConfig.setShowplacesUri("showplaces");

        final ShowPlaceSendController restController = new ShowPlaceCollectorRestController(restTemplate,
                restServerConfig);

        final var testShowPlace = new ShowPlaceDto();
        testShowPlace.setInfo("info");
        testShowPlace.setAddress("address");

        final var response = new ResponseEntity<String>(RESULT, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(response);

        restController.send(testShowPlace);

        Mockito.verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class));
    }
}
