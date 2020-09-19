package source.walkspb;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public class PageHandlerImpl implements PageHandler {
    private final URL baseUrl;

    public PageHandlerImpl() {
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
        if (currentPageNum >= WalkspbBuilding.getPageCnt()) {
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
        final String pageParametr = "start=";
        final int index = urlString.indexOf(pageParametr);
        if (index == -1) {
            return 0;
        }
        final String numString = urlString.substring(index + pageParametr.length(), urlString.length() - 1);
        return Integer.parseInt(numString);
    }
}
