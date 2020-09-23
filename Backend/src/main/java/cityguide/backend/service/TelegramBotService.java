package cityguide.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Location;

import cityguide.datastorage.DataStorage;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;
import cityguide.telegram.Telegram;
import cityguide.telegram.bot.TelegramBot;

public class TelegramBotService {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);
    private final TelegramBot cityGuideBot;
    private final DataStorage dataStorage;

    public TelegramBotService(TelegramBot cityGuideBot, DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.cityGuideBot = cityGuideBot;
        this.cityGuideBot.setMessageHandler(message -> {
            logger.info("On new message. Recived new message {}", message);
            if (message.hasText()) {
                return Optional.of(message.getText());
            }
            if (message.hasLocation()) {
                final Location location = message.getLocation();
                final List<Double> coordinates = new ArrayList<>();
                coordinates.add(Double.valueOf(location.getLatitude().toString()));
                coordinates.add(Double.valueOf(location.getLongitude().toString()));
                final GeoPosition geoPosition = new GeoPosition().setCoordinates(coordinates);
                final List<ShowPlace> showPlaces = this.dataStorage.getNearest(geoPosition, 100);
                final String responseMessage = (showPlaces.isEmpty()) ? "Ничего не найдено"
                        : showPlaces.get(0).getDescriptionList().stream().map(data -> data.getInfo())
                                .collect(Collectors.toList()).toString();
                return Optional.of(responseMessage);
            }
            return Optional.empty();
        });
    }

    public void start() {
        Telegram.startBot(cityGuideBot);
    }
}
