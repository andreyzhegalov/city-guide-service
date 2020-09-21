package cityguide.backend;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cityguide.backend.config.BackendConfig;
import cityguide.backend.service.ShowPlaceDataCollectorService;

public class DataStorageExecutor {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BackendConfig.class, ShowPlaceDataCollectorService.class);
        final var dataCollectorService = ctx.getBean(ShowPlaceDataCollectorService.class);
        dataCollectorService.loadData();
    }
}

