package cityguide.geocoder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "cityguide.geocoder")
@Configuration("geoCoderConfig")
@Setter
@Getter
public class GeoCoderConfig {
    private String url;
    private String token;
}

