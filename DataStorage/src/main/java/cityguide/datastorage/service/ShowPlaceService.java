package cityguide.datastorage.service;

import java.util.List;
import java.util.Optional;

import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;

public interface ShowPlaceService {

    void insertUpdateData(ShowPlace showPlace);

    Optional<ShowPlace> getData(String address);

    List<ShowPlace> getNearest(GeoPosition geoPosition, double radiusInMeter);

    List<ShowPlace> getAllData();
}

