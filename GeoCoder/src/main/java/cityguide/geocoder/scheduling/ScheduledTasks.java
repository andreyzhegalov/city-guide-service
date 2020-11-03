package cityguide.geocoder.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cityguide.geocoder.dto.AddressDto;
import cityguide.geocoder.service.GeoCoderService;

@Component
public class ScheduledTasks {
    private final GeoCoderService<AddressDto> geoCoderService;

    public ScheduledTasks(GeoCoderService<AddressDto> geoCoderService) {
        this.geoCoderService = geoCoderService;
    }

    @Scheduled(cron ="0 30 1 ? * MON")
    public void onTimer() {
        geoCoderService.fillAllAddresses();
    }
}
