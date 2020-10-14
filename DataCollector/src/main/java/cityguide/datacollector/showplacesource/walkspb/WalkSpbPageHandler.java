package cityguide.datacollector.showplacesource.walkspb;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import cityguide.datacollector.showplacesource.sitesource.PageHandler;
import org.springframework.stereotype.Component;

import cityguide.datacollector.config.WalkSpbSiteConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WalkSpbPageHandler implements PageHandler {
    private final URL baseUrl;
    private final WalkSpbSiteConfig walkSpbSiteConfig;

    public WalkSpbPageHandler(WalkSpbSiteConfig siteConfig) {
        log.info(siteConfig.toString());

        this.walkSpbSiteConfig = siteConfig;
        try {
            baseUrl = new URL(this.walkSpbSiteConfig.getBaseUrl());
        } catch (MalformedURLException e) {
            throw new WalkSpbException(e.toString());
        }
    }

    @Override
    public URL getFistPage() {
        return baseUrl;
    }

    @Override
    public Optional<URL> getNextPage(URL currentPage) {
        final int currentPageNum = getCurrentPageNumber(currentPage);
        if (currentPageNum >= walkSpbSiteConfig.getPageCount() - 1) {
            return Optional.empty();
        }
        return Optional.of(getPageUrl(currentPageNum + 1));
    }

    @Override
    public URL getLastPage() {
        return getPageUrl(walkSpbSiteConfig.getPageCount() - 1);
    }

    private URL getPageUrl(int pageNumber) {
        return (pageNumber == 0) ? baseUrl : makeUrl(pageNumber);
    }

    private URL makeUrl(int pageNumber) {
        try {
            return new URL(baseUrl.toString() + "?start=" + walkSpbSiteConfig.getItemOnPage() * pageNumber);
        } catch (Exception e) {
            throw new WalkSpbException(e.toString());
        }
    }

    private int getCurrentPageNumber(URL url) {
        final String urlString = url.toString();
        final String pageParameter = "start=";
        final int index = urlString.indexOf(pageParameter);
        if (index == -1) {
            return 0;
        }
        final String numString = urlString.substring(index + pageParameter.length());
        return Integer.parseInt(numString) / walkSpbSiteConfig.getItemOnPage();
    }
}
