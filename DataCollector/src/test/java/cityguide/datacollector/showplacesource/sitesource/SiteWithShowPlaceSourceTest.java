package cityguide.datacollector.showplacesource.sitesource;

import static org.mockito.ArgumentMatchers.any;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import cityguide.datacollector.dto.ShowPlaceDto;

class SiteWithShowPlaceSourceTest {

    static class TestHandler implements Consumer<ShowPlaceDto> {
        @Override
        public void accept(ShowPlaceDto t) {
        }
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void collectWithoutConsumers() {
        final var siteHandler = Mockito.mock(SiteHandler.class);
        final var siteSource = new SiteSource(siteHandler);

        siteSource.collect();
        Mockito.verify(siteHandler, Mockito.never()).getAllPageUrl();
    }

    @Test
    void collect() throws MalformedURLException {
        final var siteHandler = Mockito.mock(SiteHandler.class);
        final var siteSource = new SiteSource(siteHandler);
        final var handler = Mockito.mock(TestHandler.class);
        siteSource.setReceiver(handler);

        Mockito.when(siteHandler.getAllPageUrl()).thenReturn(Collections.singletonList(new URL("https://test.com")));
        Mockito.when(siteHandler.getAllItemsPageUrl(any())).thenReturn(Collections.singletonList(new URL("https://test.com/item")));
        Mockito.when(siteHandler.getShowPlace(any())).thenReturn(Optional.of(new ShowPlaceDto()));

        siteSource.collect();

        Mockito.verify(siteHandler).getAllPageUrl();
        Mockito.verify(siteHandler).getAllItemsPageUrl(any());
        Mockito.verify(siteHandler).getShowPlace(any());
        Mockito.verify(handler).accept(any());
    }
}
