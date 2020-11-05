package cityguide.telegrambot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "cityguide.restserver")
@Configuration("restServerConfig")
public class RestServerConfig {
    private String url;
    private String showplacesUri;
}

