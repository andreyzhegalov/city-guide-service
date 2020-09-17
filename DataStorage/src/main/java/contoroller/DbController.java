package contoroller;

import java.util.List;
import java.util.Optional;

import model.Address;
import model.GeoPosition;
import model.ShowPlace;

public interface DbController {
    void insertUpdateData(ShowPlace showPlace);

    Optional<ShowPlace> getData(Address adress);

    List<ShowPlace> getNearest(GeoPosition geoPosition, double radiusInMeter);

    List<ShowPlace> getAllData();
}
