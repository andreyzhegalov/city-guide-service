package cityguide.datacollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "cityguide.sources.walkspb", ignoreUnknownFields = false)
@Component
public class WalkSpbSiteConfig {
    private String baseUrl;
    private int pageCount;
    private int itemOnPage;
}

