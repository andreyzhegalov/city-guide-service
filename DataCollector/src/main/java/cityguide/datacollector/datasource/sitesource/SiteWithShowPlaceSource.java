package cityguide.datacollector.datasource.sitesource;

import java.util.function.Consumer;

import cityguide.datacollector.datasource.DataSourceException;
import cityguide.datacollector.datasource.ShowPlaceSource;
import cityguide.datacollector.dto.ShowPlaceDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SiteWithShowPlaceSource implements ShowPlaceSource {
    private Consumer<ShowPlaceDto> consumer;
    private final PageReciver pageReciver;
    private final PageHandler pageHandler;
    private final ItemExtractor itemExtractor;
    private final ItemParser itemParser;

    public SiteWithShowPlaceSource(PageReciver pageReciver, PageHandler pageHandler, ItemExtractor itemExtractor, ItemParser itemParser) {
        this.pageReciver = pageReciver;
        this.pageHandler = pageHandler;
        this.itemExtractor = itemExtractor;
        this.itemParser = itemParser;
    }

    @Override
    public void collect() {
        if (consumer == null) {
            return;
        }
        final var thread = new Thread(() -> {
            var currentPage = pageHandler.getFistPage();
            while (true) {
                log.info("Current page {}", currentPage);
                final var htmlWithItems = pageReciver.getHtml(currentPage);
                final var itemsUrlList = itemExtractor.getItemUrl(htmlWithItems);
                for (final var itemUrl : itemsUrlList) {
                    log.debug("itemUrl  {}", itemUrl);
                    final var html = pageReciver.getHtml(itemUrl);
                    final var mayBeShowPlace = itemParser.getShowPlace(html);
                    if (mayBeShowPlace.isPresent()) {
                        consumer.accept(mayBeShowPlace.get());
                    }
                    sleep();
                }
                final var mayBeCurrentPage = pageHandler.getNextPage(currentPage);
                if (mayBeCurrentPage.isEmpty()) {
                    break;
                }
                currentPage = mayBeCurrentPage.get();
                sleep();
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new DataSourceException(e.toString());
        }
    }

    private void sleep() {
        try {
            Thread.sleep(getRandomNumber(1_000, 3 * 1_000));
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public void setHandler(Consumer<ShowPlaceDto> newShowPlaceHandler) {
        this.consumer = newShowPlaceHandler;
    }

}
