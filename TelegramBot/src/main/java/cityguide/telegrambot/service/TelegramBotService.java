package cityguide.telegrambot.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import cityguide.telegrambot.controller.DataStorageRestController;
import cityguide.telegrambot.telegram.bot.TelegramBot;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public final class TelegramBotService {
    private static final int SEARCH_RADIUS = 100;
    private final DataStorageRestController restController;

    public TelegramBotService(TelegramBot telegramBot, DataStorageRestController restController) {
        this.restController = restController;
        telegramBot.setMessageHandler(this::onMessage);
    }

    public Optional<String> onMessage(Message message) {
        log.info("On new message. Received new message {}", message);
        if (message.hasText()) {
            // echo for simple message
            return Optional.of(message.getText());
        }
        if (message.hasLocation()) {
            final Location location = message.getLocation();
            return restController.getShowPlaceDescription(location.getLatitude(), location.getLongitude(),
                    SEARCH_RADIUS);
        }
        return Optional.empty();
    }
}
