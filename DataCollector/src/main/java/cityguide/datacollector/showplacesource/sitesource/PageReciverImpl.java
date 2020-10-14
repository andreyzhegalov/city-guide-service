package cityguide.datacollector.showplacesource.sitesource;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class PageReciverImpl implements PageReciver {
    @Override
    public String getHtml(URL url) {
        final Document doc;
        try {
            doc = Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            throw new SiteSourceException(e.toString());
        }
        return doc.toString();
    }
}
