package cityguide.telegrambot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import cityguide.telegrambot.telegram.Telegram;
import cityguide.telegrambot.telegram.bot.TelegramBot;

@Configuration
@ComponentScan("cityguide")
public class ContextConfig {
    @Bean
    public TelegramBot cityGuideBot(TelegramBotConfig telegramBotConfig) {
        Telegram.initContext();
        final var telegramBot = new TelegramBot(telegramBotConfig.getName(), telegramBotConfig.getToken());
        Telegram.startBot(telegramBot);
        return telegramBot;
    }

    @Bean
    public RestTemplate getRestController() {
        return new RestTemplate();
    }
}
