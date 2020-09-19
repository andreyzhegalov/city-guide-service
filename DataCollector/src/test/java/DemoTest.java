import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoTest {
    private final static Logger logger = LoggerFactory.getLogger(DemoTest.class);
    private final static String START_URL = "https://walkspb.ru/istoriya-peterburga/zd";

    @Test
    public void officialTest() throws IOException {
        assertThat(true).isEqualTo(true);

        final Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        logger.info(doc.title());
        final Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
            logger.info("{}\n\t{}", headline.attr("title"), headline.absUrl("href"));
        }
    }

    @Test
    public void getAllItemsUrl() throws IOException {
        // final ClassLoader classLoader = getClass().getClassLoader();
        // final File file = new
        // File(classLoader.getResource("walkspb_zd.html").getFile());
        // final String html = file.toString();
        // final Document doc = Jsoup.parse(html);
        final Document doc = Jsoup.connect(START_URL).get();
        final Elements items = doc.select("div.item:has(div.obj_params)");
        assertThat(items.size()).isNotZero();

        logger.info("finded {}", items.size());
        for (Element item : items) {
            logger.info("{}", item);
        }

        logger.info("items url");
        for (Element item : items) {
            Element description = item.selectFirst("a[href]");
            logger.info("{}", description.absUrl("href"));
        }
    }

    public String getPageUrl(int number) {
        final int ITEM_ON_PAGE = 20;
        return (number == 0) ? START_URL : START_URL + "?start=" + ITEM_ON_PAGE * number;
    }

    @Test
    public void getNotExistingPage() throws IOException{
        final var pageUrl = getPageUrl(Integer.MAX_VALUE);
        logger.info(pageUrl);
        // final Document doc = Jsoup.connect(pageUrl).get();
        // logger.info(doc.toString());

    }

    @Test
    public void getFirstPage() throws IOException{
        final var pageUrl = getPageUrl(0);
        logger.info(pageUrl);
        final Document doc = Jsoup.connect(pageUrl).get();
        assertThat(doc.empty());
    }

    @Test
    public void getNextPage() throws IOException {
        final Document doc = Jsoup.connect(START_URL).get();
        // final Document doc = Jsoup.connect(START_URL + "?start=20").get();
        final Elements curPage = doc.select("li>span.pagenav");
        logger.info("Current page");
        for (Element item : curPage) {
            logger.info("{} text {}", item, item.text());
        }

        // final Elements next = doc.select("li>a.pagenav");
        // final Elements startEnd = doc.select("li>a.pagenav[title]");
        // // next.removeAll(startEnd);
        // for (Element item : next) {
        // logger.info("{}", item);
        // }

    }
}
