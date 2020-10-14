package cityguide.datacollector.datasource.walkspb;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.Charset;

import cityguide.datacollector.showplacesource.walkspb.WalkSpbItemExtractor;
import org.junit.jupiter.api.Test;

public class WalkSpbItemExtractorTest {
    private final static String ITEMS_HTML = "walkspb_zd.html";

    private String getHtml() throws IOException{
        final ClassLoader classLoader= getClass().getClassLoader();
        String html = "";
        try( final var inputSteam = classLoader.getResourceAsStream(ITEMS_HTML))
        {
            final var array = inputSteam.readAllBytes();
            final Charset charset = Charset.forName("UTF-8");
            html = new String(array, charset);
        }
        return html;
    }

    @Test
    void getItemsUrlTestForEmptyHtml(){
        final var itemExtractor = new WalkSpbItemExtractor();
        final var html = "";
        assertThat(itemExtractor.getItemUrls(html)).isEmpty();
    }

    @Test
    void getItemsUrlTest() throws IOException{
        final var itemExtractor = new WalkSpbItemExtractor();
        final var html = getHtml();
        assertThat(itemExtractor.getItemUrls(html)).hasSize(20);
    }
}

