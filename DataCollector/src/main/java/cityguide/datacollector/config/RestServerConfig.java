package cityguide.datacollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "cityguide.restserver", ignoreUnknownFields = false)
@Component
public class RestServerConfig {
    private String url;
    private String showplacesUri;
}
