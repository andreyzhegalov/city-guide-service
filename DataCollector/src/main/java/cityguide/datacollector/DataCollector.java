package cityguide.datacollector;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cityguide.datacollector.source.walkspb.ItemExtractor;
import cityguide.datacollector.source.walkspb.ItemParser;
import cityguide.datacollector.source.walkspb.PageHandlerImpl;

public class DataCollector {
    private static final Logger logger = LoggerFactory.getLogger(DataCollector.class);
    private Consumer<DataType> callback;

    public void start() {
        final var pageHandler = new PageHandlerImpl();

        final var thread = new Thread(() -> {
            var curPage = pageHandler.getFistPage();
            while (true) {
                logger.info("Current page {}", curPage);
                final var itemsUrlList = new ItemExtractor().getItemUrl(curPage);
                for (final var itemUrl : itemsUrlList) {
                    logger.debug("itemUrl  {}", itemUrl);
                    final var itemParser = new ItemParser(itemUrl);
                    final var address = itemParser.getAddresses();
                    final var description = itemParser.getDescription();
                    final var result = new DataType();
                    result.setAddress(address);
                    result.setDescription(description);
                    if (callback != null) {
                        callback.accept(result);
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
            throw new DataCollectorException(e.toString());
        }
    }

    public void setCallback(Consumer<DataType> callback) {
        this.callback = callback;
    }

    private void sleep() {
        try {
            Thread.sleep(getRandomNumber(1_000, 3 * 1_000));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
