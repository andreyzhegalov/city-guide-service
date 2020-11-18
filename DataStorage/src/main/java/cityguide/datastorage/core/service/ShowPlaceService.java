package cityguide.datastorage.core.service;

import java.util.List;

import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;

public interface ShowPlaceService {

    String getDescription(Location location, double searchRadius);

    void insertUpdateShowplace(ShowPlace showPlace);

    void updateLocation(String address, Location location);

    List<ShowPlace> getShowPlaces(boolean withCoord);
}
