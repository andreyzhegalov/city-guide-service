package cityguide.telegrambot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties(prefix = "cityguide.telegram")
@Configuration("telegramBotConfig")
public class TelegramBotConfig {
    private String name;
    private String token;
}

