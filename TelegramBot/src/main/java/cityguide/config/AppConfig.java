package cityguide.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cityguide.telegram.Telegram;
import cityguide.telegram.bot.TelegramBot;

@Configuration
@ComponentScan("cityguide")
public class AppConfig {
    @Autowired private TelegramBotConfig telegramBotConfig;

    @Bean
    public TelegramBot cityGuideBot() {
        Telegram.initContext();
        return new TelegramBot(telegramBotConfig.getName(), telegramBotConfig.getToken());
    }
}

