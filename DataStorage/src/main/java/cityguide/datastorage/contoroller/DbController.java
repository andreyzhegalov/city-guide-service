package cityguide.datastorage.contoroller;

import java.util.List;
import java.util.Optional;

import cityguide.datastorage.model.Address;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;

public interface DbController {
    void insertUpdateData(ShowPlace showPlace);

    Optional<ShowPlace> getData(Address adress);

    List<ShowPlace> getNearest(GeoPosition geoPosition, double radiusInMeter);

    List<ShowPlace> getAllData();
}
