package cityguide.datacollector.datasource.walkspb;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import cityguide.datacollector.datasource.sitesource.ItemParser;
import cityguide.datacollector.dto.ShowPlaceDto;

@Component
public class WalkSpbItemParser implements ItemParser {
    private Document document;

    private List<String> getAddresses() {
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

    private String getDescription() {
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

    @Override
    public Optional<ShowPlaceDto> getShowPlace(URL url) {
        try {
            this.document = Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            return Optional.empty();
        }
        final var showPlace = new ShowPlaceDto();
        showPlace.setAddress(getAddresses().get(0));
        showPlace.setInfo(getDescription());
        return Optional.of(showPlace);
    }
}
