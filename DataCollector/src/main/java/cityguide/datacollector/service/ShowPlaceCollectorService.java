package cityguide.datacollector.service;

import cityguide.datacollector.showplacesource.ShowPlaceSource;
import cityguide.datacollector.dto.ShowPlaceDto;

public interface ShowPlaceCollectorService {
    void start();

    void addShowPlaceSource(ShowPlaceSource newSource);

    void send(ShowPlaceDto addressData);
}
