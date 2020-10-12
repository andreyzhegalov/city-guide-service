package cityguide.datacollector.datasource.sitesource;

import java.util.function.Consumer;

import cityguide.datacollector.datasource.DataSourceException;
import cityguide.datacollector.datasource.ShowPlaceSource;
import cityguide.datacollector.dto.ShowPlaceDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SiteWithShowPlaceSource implements ShowPlaceSource {
    private Consumer<ShowPlaceDto> consumer;
    private final PageHandler pageHandler;
    private final ItemExtractor itemExtractor;
    private final ItemParser itemParser;

    public SiteWithShowPlaceSource(PageHandler pageHandler, ItemExtractor itemExtractor, ItemParser itemParser) {
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
            var curPage = pageHandler.getFistPage();
            while (true) {
                log.info("Current page {}", curPage);
                final var itemsUrlList = itemExtractor.getItemUrl(curPage);
                for (final var itemUrl : itemsUrlList) {
                    log.debug("itemUrl  {}", itemUrl);
                    final var mayBeShowPlace = itemParser.getShowPlace(itemUrl);
                    if (mayBeShowPlace.isPresent()) {
                        consumer.accept(mayBeShowPlace.get());
                    }
                    sleep();
                }
                final var mayBeCurPage = pageHandler.getNextPage(curPage);
                if (mayBeCurPage.isEmpty()) {
                    break;
                }
                curPage = mayBeCurPage.get();
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
