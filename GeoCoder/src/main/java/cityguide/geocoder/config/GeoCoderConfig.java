package cityguide.geocoder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cityguide.geocoder")
@Configuration("geoCoderConfig")
public class GeoCoderConfig {
    private String url;
    private String token;

    public GeoCoderConfig() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}

