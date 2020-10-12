package cityguide.datacollector.datasource.walkspb;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.springframework.stereotype.Component;

import cityguide.datacollector.datasource.sitesource.PageHandler;

@Component
public class WalkSpbPageHandler implements PageHandler {
    private final URL baseUrl;

    public WalkSpbPageHandler() {
        try {
            baseUrl = new URL(WalkspbBuilding.getBaseUrl());
        } catch (MalformedURLException e) {
            throw new WalkspbException(e.toString());
        }
    }

    @Override
    public URL getFistPage() {
        return baseUrl;
    }

    @Override
    public Optional<URL> getNextPage(URL currentPage) {
        final int currentPageNum = getCurrentPageNumber(currentPage);
        if (currentPageNum >= WalkspbBuilding.getPageCnt() - 1) {
            return Optional.empty();
        }
        return Optional.of(getPageUrl(currentPageNum + 1));
    }

    @Override
    public URL getLastPage() {
        return getPageUrl(WalkspbBuilding.getPageCnt() - 1);
    }

    private URL getPageUrl(int pageNumber) {
        return (pageNumber == 0) ? baseUrl : makeUrl(pageNumber);
    }

    private URL makeUrl(int pageNumber) {
        try {
            return new URL(baseUrl.toString() + "?start=" + WalkspbBuilding.getItemOnPage() * pageNumber);
        } catch (Exception e) {
            throw new WalkspbException(e.toString());
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
        return Integer.parseInt(numString) / WalkspbBuilding.getItemOnPage();
    }
}
