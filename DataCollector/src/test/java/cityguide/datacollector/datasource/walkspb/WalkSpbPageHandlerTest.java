package cityguide.datacollector.datasource.walkspb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import cityguide.datacollector.config.WalkSpbSiteConfig;
import cityguide.datacollector.showplacesource.walkspb.WalkSpbException;
import cityguide.datacollector.showplacesource.walkspb.WalkSpbPageHandler;

public class WalkSpbPageHandlerTest {
    private final WalkSpbSiteConfig siteConfig;

    WalkSpbPageHandlerTest() {
        siteConfig = new WalkSpbSiteConfig();
        siteConfig.setBaseUrl("https://walkspb.ru/istoriya-peterburga/zd");
        siteConfig.setPageCount(35);
        siteConfig.setItemOnPage(20);
    }

    @Test
    void ctrForInvalidBaseSiteUrl() {
        siteConfig.setBaseUrl("");
        assertThatThrownBy(() -> {
            new WalkSpbPageHandler(siteConfig);
        }).isInstanceOf(WalkSpbException.class);
    }

    @Test
    public void getFirstPageTest() {
        assertThat(new WalkSpbPageHandler(siteConfig).getFistPage().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd");
    }

    @Test
    public void getLastPage() {
        assertThat(new WalkSpbPageHandler(siteConfig).getLastPage().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd?start=680");
    }

    @Test
    public void getNextPageWhenExist() {
        final var pageHandler = new WalkSpbPageHandler(siteConfig);
        final var currentPage = pageHandler.getFistPage();
        final var mayBeNextPage = pageHandler.getNextPage(currentPage);
        assertThat(mayBeNextPage).isPresent();
        assertThat(mayBeNextPage.get().toString()).isEqualTo("https://walkspb.ru/istoriya-peterburga/zd?start=20");
    }

    @Test
    public void testIncrement() {
        final var pageHandler = new WalkSpbPageHandler(siteConfig);
        final var currentPage = pageHandler.getFistPage();

        var mayBeNextPage = pageHandler.getNextPage(currentPage);

        assertThat(mayBeNextPage).isPresent();

        mayBeNextPage = pageHandler.getNextPage(mayBeNextPage.get());
        assertThat(mayBeNextPage).isPresent();
        assertThat(mayBeNextPage.get().toString()).isEqualTo("https://walkspb.ru/istoriya-peterburga/zd?start=40");
    }

    @Test
    public void getNextPageForLastPage() {
        final var pageHandler = new WalkSpbPageHandler(siteConfig);
        final var currentPage = pageHandler.getLastPage();
        assertThat(pageHandler.getNextPage(currentPage)).isEmpty();
    }
}
