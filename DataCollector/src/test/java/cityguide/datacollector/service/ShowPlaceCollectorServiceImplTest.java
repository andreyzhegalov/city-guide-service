package cityguide.datacollector.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import cityguide.datacollector.controller.ShowPlaceCollectorRestController;
import cityguide.datacollector.showplacesource.ShowPlaceSource;
import cityguide.datacollector.dto.ShowPlaceDto;

class ShowPlaceCollectorServiceImplTest {

    @Test
    void showPlaceSourceCollectShouldBeInvokedAfterServiceStart() {
        final ShowPlaceCollectorService showPlaceService = new ShowPlaceCollectorServiceImpl(null);
        final ShowPlaceSource showPlaceSource1 = Mockito.mock(ShowPlaceSource.class);
        final ShowPlaceSource showPlaceSource2 = Mockito.mock(ShowPlaceSource.class);

        showPlaceService.addShowPlaceSource(showPlaceSource1);
        showPlaceService.addShowPlaceSource(showPlaceSource2);

        showPlaceService.start();

        Mockito.verify(showPlaceSource1).collect();
        Mockito.verify(showPlaceSource2).collect();
    }

    @Test
    void expectThatRestControllerSendData() {
        final ShowPlaceCollectorRestController restController = Mockito.mock(ShowPlaceCollectorRestController.class);
        final ShowPlaceCollectorService showPlaceService = new ShowPlaceCollectorServiceImpl(restController);
        final var testShowPlace = new ShowPlaceDto();
        testShowPlace.setInfo("info");
        testShowPlace.setAddress("address");

        showPlaceService.send(testShowPlace);

        Mockito.verify(restController).send(testShowPlace);
    }
}
