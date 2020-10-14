package cityguide.datacollector.showplacesource.sitesource;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import cityguide.datacollector.dto.ShowPlaceDto;

public interface SiteHandler {

    Optional<ShowPlaceDto> getShowPlace(URL itemUrl);

    List<URL> getAllItemsPageUrl(URL pageUrl);

    List<URL> getAllPageUrl();
}

