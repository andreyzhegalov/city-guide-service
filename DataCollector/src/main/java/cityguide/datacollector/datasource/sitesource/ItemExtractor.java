package cityguide.datacollector.datasource.sitesource;

import java.net.URL;
import java.util.List;

public interface ItemExtractor {
    public List<URL> getItemUrl(URL pageWithItem);
}

