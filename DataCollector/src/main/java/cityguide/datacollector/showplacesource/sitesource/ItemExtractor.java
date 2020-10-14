package cityguide.datacollector.showplacesource.sitesource;

import java.net.URL;
import java.util.List;

public interface ItemExtractor {
    public List<URL> getItemUrls(String html);
}

