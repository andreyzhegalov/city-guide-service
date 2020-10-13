package cityguide.datacollector.datasource.sitesource;

import java.net.URL;
import java.util.Optional;

import cityguide.datacollector.dto.ShowPlaceDto;

public interface ItemParser {

    Optional<ShowPlaceDto> getShowPlace(URL url);

    Optional<ShowPlaceDto> getShowPlace(String html);
}

