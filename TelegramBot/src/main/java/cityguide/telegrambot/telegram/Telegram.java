package cityguide.telegrambot.telegram;

import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

@Service
public final class Telegram {
    public static void initContext() {
        ApiContextInitializer.init();
    }

    public static void startBot(LongPollingBot bot) {
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}
