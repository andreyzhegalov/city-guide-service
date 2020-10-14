package cityguide.datacollector.showplacesource.walkspb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import cityguide.datacollector.dto.ShowPlaceDto;
import cityguide.datacollector.showplacesource.sitesource.ItemParser;

@Component
public class WalkSpbItemParser implements ItemParser {
    private static final String city = "г.Санкт-Петербург";

    @Override
    public Optional<ShowPlaceDto> getShowPlace(String html) {
        final Document document = Jsoup.parse(html);
        final var showPlace = new ShowPlaceDto();
        final var addresses = getAddresses(document);
        if (addresses.isEmpty()) {
            return Optional.empty();
        }
        showPlace.setAddress(addresses.get(0));
        showPlace.setInfo(getDescription(document));
        return Optional.of(showPlace);
    }

    private List<String> getAddresses(Document document) {
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

    private String getDescription(Document document) {
        final StringBuilder sb = new StringBuilder();
        final var description = document.select("div.fulltext>p, div.quote");
        for (final var p : description) {
            sb.append(p.text());
            sb.append("\n");
        }
        return sb.toString();
    }

    private String addCity(String address) {
        return city + address;
    }
}
