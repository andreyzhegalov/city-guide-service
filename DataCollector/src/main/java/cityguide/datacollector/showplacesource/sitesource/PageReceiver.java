package cityguide.datacollector.showplacesource.sitesource;

import java.net.URL;

public interface PageReceiver {
    String getHtml(URL url);
}

