package cityguide.backend.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cityguide.telegram.Telegram;
import cityguide.telegram.bot.CityGuideBot;

public class TelegramBotService {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBotService.class);
    private final CityGuideBot cityGuideBot;

    public TelegramBotService(CityGuideBot cityGuideBot){
        this.cityGuideBot = cityGuideBot;
        this.cityGuideBot.setMessageHandler(message -> {
            logger.info("On new message. Recived new message {}", message );
            if (message.hasText()) {
                return Optional.of(message.getText());
            }
            if (message.hasLocation()) {
                logger.info("message has location");
                return Optional.of(" Recive location " + message.getLocation().toString());
            }
            return Optional.empty();
        });
    }

    public void start(){
        Telegram.startBot(cityGuideBot);
    }
}

