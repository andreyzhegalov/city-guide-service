package cityguide.datacollector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cityguide.datacollector.controller.DataCollectorRestController;
import cityguide.datacollector.dto.AddressData;
import cityguide.datacollector.source.walkspb.ItemExtractor;
import cityguide.datacollector.source.walkspb.ItemParser;
import cityguide.datacollector.source.walkspb.PageHandlerImpl;

@Service
public class DataCollectorService {
    private static final Logger logger = LoggerFactory.getLogger(DataCollectorService.class);
    private DataCollectorRestController restController;

    public DataCollectorService(DataCollectorRestController restController){
        this.restController = restController;
    }

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
                    if(itemParser.getAddresses().isEmpty()){
                        logger.warn("No address in item {}", itemParser.getDescription().subSequence(0, 20));
                        continue;
                    }
                    final var result = new AddressData();
                    result.setAddress(itemParser.getAddresses().get(0));
                    result.setInfo(itemParser.getDescription());

                    sendData(result);
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

    public void sendData(AddressData addressData){
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

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
