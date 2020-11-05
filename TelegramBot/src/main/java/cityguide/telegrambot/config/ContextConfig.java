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
        return new TelegramBot(telegramBotConfig.getName(), telegramBotConfig.getToken());
    }

    @Bean
    public RestTemplate getRestController(){
        return new RestTemplate();
    }
}

