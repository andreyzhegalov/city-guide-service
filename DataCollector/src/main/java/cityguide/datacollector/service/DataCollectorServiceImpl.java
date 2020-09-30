package cityguide.datacollector.service;

import java.net.URL;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cityguide.datacollector.controller.ShowPlaceCollectorRestController;
import cityguide.datacollector.dto.ShowPlaceDto;
import cityguide.datacollector.source.walkspb.ItemExtractor;
import cityguide.datacollector.source.walkspb.ItemParser;
import cityguide.datacollector.source.walkspb.PageHandlerImpl;

@Service
public class DataCollectorServiceImpl implements ShowPlaceCollectorService {
    private static final Logger logger = LoggerFactory.getLogger(DataCollectorServiceImpl.class);
    private final ShowPlaceCollectorRestController restController;

    public DataCollectorServiceImpl(ShowPlaceCollectorRestController restController) {
        this.restController = restController;
    }

    @Override
    public void start() {
        final var pageHandler = new PageHandlerImpl();

        final var thread = new Thread(() -> {
            var curPage = pageHandler.getFistPage();
            while (true) {
                logger.info("Current page {}", curPage);
                final var itemsUrlList = new ItemExtractor().getItemUrl(curPage);
                for (final var itemUrl : itemsUrlList) {
                    logger.debug("itemUrl  {}", itemUrl);
                    final var mayBeShowPlace = getShowPlace(itemUrl);
                    if (mayBeShowPlace.isPresent()) {
                        send(mayBeShowPlace.get());
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
            throw new DataCollectorServiceException(e.toString());
        }
    }

    @Override
    public Optional<ShowPlaceDto> getShowPlace(URL url) {
        final var itemParser = new ItemParser(url);
        if (itemParser.getAddresses().isEmpty()) {
            logger.warn("No address in item {}", itemParser.getDescription().subSequence(0, 20));
            return Optional.empty();
        }
        final var showPlace = new ShowPlaceDto();
        showPlace.setAddress(itemParser.getAddresses().get(0));
        showPlace.setInfo(itemParser.getDescription());
        return Optional.of(showPlace);
    }

    @Override
    public void send(ShowPlaceDto addressData) {
        restController.sendPost(addressData);
    }

    private void sleep() {
        try {
            Thread.sleep(getRandomNumber(1_000, 3 * 1_000));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
