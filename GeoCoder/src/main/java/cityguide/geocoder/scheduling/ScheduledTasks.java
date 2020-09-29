package cityguide.geocoder.scheduling;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import cityguide.geocoder.service.GeoCoderService;

@Component
public class ScheduledTasks {
    private final GeoCoderService geoCoderService;

    public ScheduledTasks(GeoCoderService geoCoderService) {
        this.geoCoderService = geoCoderService;
    }

    // @Scheduled(fixedDelay = 150_000)
    @PostConstruct
    public void onTimer() {
        geoCoderService.fillAllAddresses();
    }
}
