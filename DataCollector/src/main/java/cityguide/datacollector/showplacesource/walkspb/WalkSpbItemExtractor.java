package cityguide.datacollector.showplacesource.walkspb;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cityguide.datacollector.showplacesource.sitesource.ItemExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class WalkSpbItemExtractor implements ItemExtractor {
    @Override
    public List<URL> getItemUrls(String html) {
        final var document = Jsoup.parse(html);
        final Elements items = document.select("div.item:has(div.obj_params)");
        final var itemsList = new ArrayList<URL>();
        for (Element item : items) {
            final var description = item.selectFirst("a[href]");
            final URL itemUrl;
            try {
                itemUrl = new URL(description.absUrl("href"));
            } catch (MalformedURLException e) {
                throw new WalkSpbException(e.toString());
            }
            itemsList.add(itemUrl);
        }
        return itemsList;
    }
}
