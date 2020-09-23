package cityguide;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cityguide.telegram.Telegram;
import cityguide.telegram.bot.CityGuideBot;

public class Demo {
    private static final Logger LOG = LogManager.getLogger(CityGuideBot.class);
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Bot token argument not defined");
            System.exit(1);
        }
        final String token = args[0];
        Telegram.ContextInitializer();

        final var cityGuideBot = new CityGuideBot(token);

        cityGuideBot.setMessageHandler(message -> {
            LOG.info("On new message. Recived new message {}", message );
            if (message.hasText()) {
                return Optional.of(message.getText());
            }
            if (message.hasLocation()) {
                LOG.info("message has location");
                return Optional.of(" Recive location " + message.getLocation().toString());
            }
            return Optional.empty();
        });

        Telegram.startBot(cityGuideBot);
    }
}
