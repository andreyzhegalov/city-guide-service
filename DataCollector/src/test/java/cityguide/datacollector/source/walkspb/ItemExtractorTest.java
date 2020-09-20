package cityguide.datacollector.source.walkspb;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

public class ItemExtractorTest {
    @Test
    public void getItemsUrlTest() throws MalformedURLException{
        final var pageWithItem = new URL("https://walkspb.ru/istoriya-peterburga/zd?start=20");
        assertThat(new ItemExtactor().getItemUrl(pageWithItem)).hasSize(20);
    }
}

