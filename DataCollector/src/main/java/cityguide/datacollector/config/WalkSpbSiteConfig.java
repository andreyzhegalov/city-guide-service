package cityguide.datacollector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "cityguide.sources.walkspb")
@Component
public class WalkSpbSiteConfig {
    private String baseUrl;
    private int pageCount;
    private int itemOnPage;

    public WalkSpbSiteConfig() {
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setItemOnPage(int itemOnPage) {
        this.itemOnPage = itemOnPage;
    }

    public int getItemOnPage() {
        return itemOnPage;
    }

    @Override
    public String toString() {
        return "WalkSpbSiteConfig{" +
            "baseUrl = " + getBaseUrl() +
            ", pageCount = " + getPageCount() +
            ", itemOnPage = " + getItemOnPage() +
            "}";
    }

}

