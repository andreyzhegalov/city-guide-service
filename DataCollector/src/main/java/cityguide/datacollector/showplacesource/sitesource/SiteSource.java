package cityguide.datacollector.showplacesource.sitesource;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import cityguide.datacollector.dto.ShowPlaceDto;
import cityguide.datacollector.showplacesource.ShowPlaceSource;
import cityguide.datacollector.showplacesource.ShowPlaceSourceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SiteSource implements ShowPlaceSource {
    private Consumer<ShowPlaceDto> consumer;
    private final SiteHandler siteHandler;

    public SiteSource(SiteHandler siteHandler) {
        this.siteHandler = siteHandler;
    }

    @Override
    public void collect() {
        if (consumer == null) {
            return;
        }
        final var thread = new Thread(new Worker());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new ShowPlaceSourceException(e.toString());
        }
    }

    @Override
    public void setReceiver(Consumer<ShowPlaceDto> newShowPlaceHandler) {
        this.consumer = newShowPlaceHandler;
    }

    private void sleep() {
        try {
            Thread.sleep(getRandomNumber());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            final List<URL> listPageUrl = siteHandler.getAllPageUrl();
            listPageUrl.forEach(pageUrl -> {
                final List<URL> itemsUrl = siteHandler.getAllItemsPageUrl(pageUrl);
                itemsUrl.forEach(itemUrl -> {
                    sleep();
                    final var mayBeShowPlace = siteHandler.getShowPlace(itemUrl);
                    sendShowPlace(mayBeShowPlace);
                });
            });
        }
    }

    private void sendShowPlace(Optional<ShowPlaceDto> mayBeShowPlace) {
        mayBeShowPlace.ifPresent(showPlaceDto -> consumer.accept(showPlaceDto));
    }

    private int getRandomNumber() {
        return (int) ((Math.random() * (3000 - 1000)) + 1000);
    }

}
