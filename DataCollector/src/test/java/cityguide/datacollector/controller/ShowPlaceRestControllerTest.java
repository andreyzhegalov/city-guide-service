package cityguide.datacollector.controller;

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

        final ShowPlaceCollectorRestController restController = new ShowPlaceCollectorRestController(restTemplate,
                restServerConfig);

        final var testShowPlace = new ShowPlaceDto();
        testShowPlace.setInfo("info");
        testShowPlace.setAddress("address");

        restController.send(testShowPlace);

        Mockito.verify(restTemplate).exchange(Mockito.anyString(), Mockito.eq(HttpMethod.POST), Mockito.any(), Mockito.eq(String.class));
    }
}
