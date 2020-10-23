package cityguide.datastorage.core.dao;

import java.util.List;
import java.util.Optional;

import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;

public interface ShowPlaceDao {

    void insertUpdateShowplace(ShowPlace showPlace);

    Optional<ShowPlace> getShowPlace(String address);

    List<ShowPlace> getNearest(Location location, double radiusInMeter);

    List<ShowPlace> getAllShowPlace();

    List<ShowPlace> getAllShowPlace(boolean hasCoordinate);
}

