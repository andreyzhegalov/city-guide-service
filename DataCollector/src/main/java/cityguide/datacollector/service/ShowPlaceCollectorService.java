package cityguide.datacollector.service;

import cityguide.datacollector.datasource.ShowPlaceSource;
import cityguide.datacollector.dto.ShowPlaceDto;

public interface ShowPlaceCollectorService {
    void start();

    void addShowPlaceSource(ShowPlaceSource newSource);

    // Optional<ShowPlaceDto> getShowPlace(URL url);

    void send(ShowPlaceDto addressData);
}
