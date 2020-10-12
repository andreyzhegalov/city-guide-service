package cityguide.datacollector.datasource.walkspb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PageHandlerTest {

    @Test
    public void getFirstPageTest() {
        assertThat(new WalkSpbPageHandler().getFistPage().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd");
    }

    @Test
    public void getLastPage() {
        assertThat(new WalkSpbPageHandler().getLastPage().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd?start=680");
    }

    @Test
    public void getNextPageWhenExist() {
        final var pageHandler = new WalkSpbPageHandler();
        final var currentPage = pageHandler.getFistPage();
        assertThat(pageHandler.getNextPage(currentPage).get().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd?start=20");
    }

    @Test
    public void testIncrement(){
        final var pageHandler = new WalkSpbPageHandler();
        final var currentPage = pageHandler.getFistPage();
        final var nextPage = pageHandler.getNextPage(currentPage).get();
        assertThat(pageHandler.getNextPage(nextPage).get().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd?start=40");
    }

    @Test
    public void getNextPageForLastPage() {
        final var pageHandler = new WalkSpbPageHandler();
        final var currentPage = pageHandler.getLastPage();
        assertThat(pageHandler.getNextPage(currentPage)).isEmpty();
    }
}
