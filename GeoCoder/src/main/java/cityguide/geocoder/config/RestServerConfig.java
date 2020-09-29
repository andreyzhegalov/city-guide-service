package cityguide.geocoder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cityguide.restserver")
@Configuration("restServerConfig")
public class RestServerConfig {
    private String url;
    private String showplacesUri;
    private String addressesUri;

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

    public void setAddressesUri(String addressesUri) {
        this.addressesUri = addressesUri;
    }

    public String getAddressesUri() {
        return addressesUri;
    }
}
