package cityguide.geocoder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ConfigurationProperties(prefix = "cityguide.restserver", ignoreUnknownFields = false)
@Configuration("restServerConfig")
public class RestServerConfig {
    private String url;
    private String showplacesUri;
    private String addressesUri;
}
