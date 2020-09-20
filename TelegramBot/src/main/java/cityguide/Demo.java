package cityguide;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import cityguide.telegrambot.CityGuideBot;

public class Demo {
    private static final Logger logger = LogManager.getLogger(Demo.class);

    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Bot token argument not defined");
            System.exit(1);
        }
        final String token = args[0];
        logger.info("Initializing API context...");
        ApiContextInitializer.init();
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new CityGuideBot(token));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}

