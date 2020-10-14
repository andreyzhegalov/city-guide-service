package cityguide.datacollector.datasource.walkspb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.io.IOException;
import java.nio.charset.Charset;

import cityguide.datacollector.showplacesource.walkspb.WalkSpbItemParser;
import org.junit.jupiter.api.Test;

public class WalkSpbItemParserTest {
    private final static String ITEM_HTML = "test_item.html";

    private String getItemHtml() throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        String html = "";
        try (final var inputStream = classLoader.getResourceAsStream(ITEM_HTML)) {
            final byte[] array = inputStream.readAllBytes();
            Charset charset = Charset.forName("UTF-8");
            html = new String(array, charset);
        }
        return html;
    }

    @Test
    void getShowPlaceTestForEmptyHtml() {
        final String html = "";
        assertThatCode(() -> new WalkSpbItemParser().getShowPlace(html)).doesNotThrowAnyException();
        assertThat(new WalkSpbItemParser().getShowPlace(html)).isEmpty();
    }

    @Test
    void getShowPlaceTest() throws IOException {
        final var html = getItemHtml();
        final var itemParser = new WalkSpbItemParser();
        final var showPlace = itemParser.getShowPlace(html);
        assertThat(showPlace).isPresent();
    }
}
