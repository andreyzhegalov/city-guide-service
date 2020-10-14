package cityguide.datacollector.showplacesource.sitesource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cityguide.datacollector.dto.ShowPlaceDto;

public class SiteHandlerImpl implements SiteHandler{
    private final PageReceiver pageReciver;
    private final PageHandler pageHandler;
    private final ItemExtractor itemExtractor;
    private final ItemParser itemParser;

    public SiteHandlerImpl(PageReceiver pageReciver, PageHandler pageHandler, ItemExtractor itemExtractor, ItemParser itemParser) {
        this.pageReciver = pageReciver;
        this.pageHandler = pageHandler;
        this.itemExtractor = itemExtractor;
        this.itemParser = itemParser;
    }

    @Override
    public Optional<ShowPlaceDto> getShowPlace(URL itemUrl) {
        final var html = pageReciver.getHtml(itemUrl) ;
        return itemParser.getShowPlace(html);
    }

    @Override
    public List<URL> getAllItemsPageUrl(URL pageUrl) {
        final var htmlWithItems = pageReciver.getHtml(pageUrl);
        return itemExtractor.getItemUrls(htmlWithItems);
    }

    @Override
    public List<URL> getAllPageUrl() {
        final List<URL> result = new ArrayList<>();
        var currentPage = pageHandler.getFistPage();
        result.add(currentPage);
        while (true) {
            final var mayBeNextPage = pageHandler.getNextPage(currentPage);
            if (mayBeNextPage.isEmpty()) {
                break;
            }
            result.add(mayBeNextPage.get());
            currentPage = mayBeNextPage.get();
        }
        return result;
    }
}

