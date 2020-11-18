package cityguide.datacollector.showplacesource.sitesource;

import java.util.Optional;

import cityguide.datacollector.dto.ShowPlaceDto;

public interface ItemParser {
    Optional<ShowPlaceDto> getShowPlace(String html);
}

