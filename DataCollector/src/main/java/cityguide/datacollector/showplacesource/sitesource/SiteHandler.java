package cityguide.datacollector.showplacesource.sitesource;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import cityguide.datacollector.dto.ShowPlaceDto;

public interface SiteHandler {

    List<URL> getAllPageUrl();

    List<URL> getAllItemsPageUrl(URL pageUrl);

    Optional<ShowPlaceDto> getShowPlace(URL itemUrl);
}

