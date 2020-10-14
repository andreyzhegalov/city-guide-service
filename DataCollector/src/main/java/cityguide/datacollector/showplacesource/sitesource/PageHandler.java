package cityguide.datacollector.showplacesource.sitesource;

import java.net.URL;
import java.util.Optional;

public interface PageHandler {

    URL getFistPage();

    Optional<URL> getNextPage(URL currentPage);

    URL getLastPage();
}

