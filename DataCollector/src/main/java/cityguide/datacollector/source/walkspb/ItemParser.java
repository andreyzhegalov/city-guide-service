package cityguide.datacollector.source.walkspb;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ItemParser {
    private final Document document;

    public ItemParser(URL itemUrl) {
        try {
            this.document = Jsoup.connect(itemUrl.toString()).get();
        } catch (IOException e) {
            throw new WalkspbException(e.toString());
        }
    }

    public List<String> getAddresses() {
        final var addressList = new ArrayList<String>();
        final Elements addr = document.select("div.addr>div");
        for (Element item : addr) {
            StringBuilder address = new StringBuilder();
            final Elements spans = item.select("span");
            for (final var span : spans) {
                address.append(span.text());
            }
            addressList.add(addCity(address.toString()));
        }
        return addressList;
    }

    public String getDescription() {
        final StringBuilder sb = new StringBuilder();
        final var description = document.select("div.fulltext>p, div.quote");
        for (final var p : description) {
            sb.append(p.text());
            sb.append("\n");
        }
        return sb.toString();
    }

    private String addCity(String address) {
        return "г.Санкт-Петербург " + address;
    }
}
