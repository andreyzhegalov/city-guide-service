package cityguide.datacollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cityguide.restserver")
@Configuration("restServerConfig")
public class RestServerConfig {
    private String url;
    private String showplacesUri;

    public RestServerConfig() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setShowplacesUri(String showplacesUri) {
        this.showplacesUri = showplacesUri;
    }

    public String getShowplacesUri() {
        return showplacesUri;
    }
}
