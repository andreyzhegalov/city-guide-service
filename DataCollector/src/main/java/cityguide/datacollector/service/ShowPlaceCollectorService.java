package cityguide.datacollector.service;

import java.net.URL;
import java.util.Optional;

import cityguide.datacollector.dto.ShowPlaceDto;

public interface ShowPlaceCollectorService {
    void start();

    Optional<ShowPlaceDto> getShowPlace(URL url);

    void send(ShowPlaceDto addressData);
}
