package cityguide.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cityguide.datastorage.model.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Location;

import cityguide.datastorage.DataStorage;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;
import cityguide.telegram.Telegram;
import cityguide.telegram.bot.TelegramBot;

public class TelegramBotService {
    private static final double SEARCH_RADIUS = 100; // meter
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);
    private final TelegramBot cityGuideBot;
    private final DataStorage dataStorage;

    public TelegramBotService(TelegramBot cityGuideBot, DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.cityGuideBot = cityGuideBot;
        this.cityGuideBot.setMessageHandler(message -> {
            logger.info("On new message. Recived new message {}", message);
            if (message.hasText()) {
                // echo for simple message
                return Optional.of(message.getText());
            }
            if (message.hasLocation()) {
                final Location location = message.getLocation();
                final GeoPosition geoPosition = new GeoPosition().setLatitude(location.getLatitude())
                        .setLongitude(location.getLongitude());
                final List<ShowPlace> showPlaces = this.dataStorage.getNearest(geoPosition, SEARCH_RADIUS);
                final String responseMessage = makeResponseMessage(showPlaces);
                return Optional.of(responseMessage);
            }
            return Optional.empty();
        });
    }

    private String makeResponseMessage(List<ShowPlace> showPlaces) {
        // NOTE only first description at this time
        return (showPlaces.isEmpty()) ? "Ничего не найдено"
                : showPlaces.get(0).getDescriptionList().stream().map(Description::getInfo)
                        .collect(Collectors.toList()).toString();
    }

    public void start() {
        Telegram.startBot(cityGuideBot);
    }
}
