package cityguide.backend;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cityguide.backend.config.BackendConfig;
import cityguide.backend.service.GeoDataCollectorService;

public class GeoDataCollector {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BackendConfig.class, GeoDataCollectorService.class);
        final var geoDataCollectorService = ctx.getBean(GeoDataCollectorService.class);
        geoDataCollectorService.loadData();
    }
}

