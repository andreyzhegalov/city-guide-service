package source.walkspb;

import java.net.URL;
import java.util.Optional;

public interface PageHandler {

    URL getFistPage();

    Optional<URL> getNextPage(URL currentPage);

    public URL getLastPage();
}

