package cityguide.datacollector.source.walkspb;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ItemExtractor {
    public List<URL> getItemUrl(URL pageWithItem) {
        final Document doc;
        try {
            doc = Jsoup.connect(pageWithItem.toString()).get();
        } catch (IOException e) {
            throw new WalkspbException(e.toString());
        }
        final Elements items = doc.select("div.item:has(div.obj_params)");
        final var itemsList = new ArrayList<URL>();
        for (Element item : items) {
            Element description = item.selectFirst("a[href]");
            final URL itemUrl;
            try {
                itemUrl = new URL(description.absUrl("href"));
            } catch (MalformedURLException e) {
                throw new WalkspbException(e.toString());
            }
            itemsList.add(itemUrl);
        }
        return itemsList;
    }
}
