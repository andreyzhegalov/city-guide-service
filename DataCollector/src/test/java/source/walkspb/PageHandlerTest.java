package source.walkspb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PageHandlerTest {

    @Test
    public void getFirstPageTest() {
        assertThat(new PageHandlerImpl().getFistPage().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd");
    }

    @Test
    public void getLastPage() {
        assertThat(new PageHandlerImpl().getLastPage().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd?start=680");
    }

    @Test
    public void getNextPageWhenExist() {
        final var pageHandler = new PageHandlerImpl();
        final var currentPage = pageHandler.getFistPage();
        assertThat(pageHandler.getNextPage(currentPage).get().toString())
                .isEqualTo("https://walkspb.ru/istoriya-peterburga/zd?start=20");
    }

    @Test
    public void getNextPageForLastPage() {
        final var pageHandler = new PageHandlerImpl();
        final var currentPage = pageHandler.getLastPage();
        assertThat(pageHandler.getNextPage(currentPage)).isEmpty();
    }
}
