package cityguide.datacollector.config;

import org.springframework.stereotype.Component;

import cityguide.datacollector.service.ShowPlaceCollectorService;
import cityguide.datacollector.showplacesource.sitesource.PageReciverImpl;
import cityguide.datacollector.showplacesource.sitesource.SiteHandlerImpl;
import cityguide.datacollector.showplacesource.sitesource.SiteSource;
import cityguide.datacollector.showplacesource.walkspb.WalkSpbItemExtractor;
import cityguide.datacollector.showplacesource.walkspb.WalkSpbItemParser;
import cityguide.datacollector.showplacesource.walkspb.WalkSpbPageHandler;

@Component
public class WalkSpbSourceConfig {

    public WalkSpbSourceConfig(PageReciverImpl pageReciver, ShowPlaceCollectorService showPlaceCollectorService, WalkSpbPageHandler pageHandler,
            WalkSpbItemExtractor itemExtractor, WalkSpbItemParser itemParser) {
        final var walkSpbSiteHandler = new SiteHandlerImpl(pageReciver, pageHandler, itemExtractor, itemParser);
        final var walkSpbSiteParser = new SiteSource(walkSpbSiteHandler);
        showPlaceCollectorService.addShowPlaceSource(walkSpbSiteParser);
        showPlaceCollectorService.start();
    }
}
