package cityguide.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cityguide.restserver")
@Configuration("restServerConfig")
public class RestServerConfig {
    private String url;
    private String getShowplacesUri;

    public RestServerConfig() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setGetShowplacesUri(String getShowplacesUri) {
        this.getShowplacesUri = getShowplacesUri;
    }

    public String getGetShowplacesUri() {
        return getShowplacesUri;
    }
}

