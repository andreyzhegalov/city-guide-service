package cityguide.datacollector.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import cityguide.datacollector.config.RestServerConfig;
import cityguide.datacollector.dto.ShowPlaceDto;

class ShowPlaceCollectorRestControllerTest {
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

        restController.send(testShowPlace);

        Mockito.verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class));
    }
}
