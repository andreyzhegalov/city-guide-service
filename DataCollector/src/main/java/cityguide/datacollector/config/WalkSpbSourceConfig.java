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
    public WalkSpbSourceConfig(PageReciverImpl pageReceiver, ShowPlaceCollectorService showPlaceCollectorService,
            WalkSpbPageHandler pageHandler, WalkSpbItemExtractor itemExtractor, WalkSpbItemParser itemParser) {
        final var siteHandler = new SiteHandlerImpl(pageReceiver, pageHandler, itemExtractor, itemParser);
        final var siteSource = new SiteSource(siteHandler);
        showPlaceCollectorService.addShowPlaceSource(siteSource);
        showPlaceCollectorService.start();
    }
}
