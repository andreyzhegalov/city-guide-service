package cityguide.datacollector.datasource.sitesource;

import java.util.Optional;

import cityguide.datacollector.dto.ShowPlaceDto;

public interface ItemParser {
    Optional<ShowPlaceDto> getShowPlace(String html);
}

