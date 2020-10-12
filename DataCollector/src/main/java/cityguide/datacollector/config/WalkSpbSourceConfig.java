package cityguide.datacollector.config;

import org.springframework.stereotype.Component;

import cityguide.datacollector.datasource.sitesource.ItemExtractor;
import cityguide.datacollector.datasource.sitesource.ItemParser;
import cityguide.datacollector.datasource.sitesource.PageHandler;
import cityguide.datacollector.datasource.sitesource.SiteWithShowPlaceSource;
import cityguide.datacollector.datasource.walkspb.WalkSpbItemExtractor;
import cityguide.datacollector.datasource.walkspb.WalkSpbItemParser;
import cityguide.datacollector.datasource.walkspb.WalkSpbPageHandler;
import cityguide.datacollector.service.ShowPlaceCollectorService;

@Component
public class WalkSpbSourceConfig {

    public WalkSpbSourceConfig(ShowPlaceCollectorService showPlaceCollectorService, WalkSpbPageHandler pageHandler,
            WalkSpbItemExtractor itemExtractor, WalkSpbItemParser itemParser) {
        final var walkSpbSiteParser = new SiteWithShowPlaceSource((PageHandler) pageHandler,
                (ItemExtractor) itemExtractor, (ItemParser) itemParser);
        showPlaceCollectorService.addShowPlaceSource(walkSpbSiteParser);
    }
}
