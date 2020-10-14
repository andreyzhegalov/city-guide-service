package cityguide.datacollector.showplacesource.sitesource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SiteHandlerImplTest {
    @Mock
    PageReciver pageReciver;

    @Mock
    PageHandler pageHandler;

    @Mock
    ItemExtractor itemExtractor;

    @Mock
    ItemParser itemParser;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllPageUrlTest() throws MalformedURLException {
        final var siteHandler = new SiteHandlerImpl(pageReciver, pageHandler, itemExtractor, itemParser);

        Mockito.when(pageHandler.getNextPage(Mockito.any())).thenReturn(Optional.of(new URL("https://test.com")))
                .thenReturn(Optional.empty());

        assertThat(siteHandler.getAllPageUrl()).hasSize(2);

        Mockito.verify(pageHandler).getFistPage();
        Mockito.verify(pageHandler, Mockito.times(2)).getNextPage(Mockito.any());
    }

    @Test
    void getShowPlaceTest() throws MalformedURLException {
        final var siteSource = new SiteHandlerImpl(pageReciver, pageHandler, itemExtractor, itemParser);

        siteSource.getShowPlace(new URL("https:/test.com"));

        Mockito.verify(pageReciver).getHtml(Mockito.any());
        Mockito.verify(itemParser).getShowPlace(Mockito.any());
    }

    @Test
    void getAllItemsPageUrlTest() throws MalformedURLException {
        final var siteSource = new SiteHandlerImpl(pageReciver, pageHandler, itemExtractor, itemParser);
        siteSource.getAllItemsPageUrl(new URL("https://test.com"));
        Mockito.verify(pageReciver).getHtml(Mockito.any());
        Mockito.verify(itemExtractor).getItemUrls(Mockito.any());
    }
}
