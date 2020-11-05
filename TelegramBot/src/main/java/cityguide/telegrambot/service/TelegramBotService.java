package cityguide.telegrambot.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import cityguide.telegrambot.controller.DataStorageRestController;
import cityguide.telegrambot.telegram.Telegram;
import cityguide.telegrambot.telegram.bot.TelegramBot;

@Service
public class TelegramBotService {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);
    private static final int SEARCH_RADIUS = 100;
    private final DataStorageRestController restController;

    public TelegramBotService(TelegramBot telegramBot, DataStorageRestController restController) {
        this.restController = restController;
        telegramBot.setMessageHandler(this::onMessage);
        Telegram.startBot(telegramBot);
    }

    private Optional<String> onMessage(Message message) {
        logger.info("On new message. Received new message {}", message);
        if (message.hasText()) {
            // echo for simple message
            return Optional.of(message.getText());
        }
        if (message.hasLocation()) {
            final Location location = message.getLocation();
            return restController.getShowPlaceDescription(location.getLatitude(), location.getLongitude(), SEARCH_RADIUS);
        }
        return Optional.empty();
    }
}
