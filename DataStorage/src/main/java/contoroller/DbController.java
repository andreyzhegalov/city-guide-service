package contoroller;

import java.util.List;

import model.Address;
import model.GeoPosition;
import model.ShowPlace;

public interface DbController {
    void setData(ShowPlace showPlace);

    List<ShowPlace> getData(Address adress);

    List<ShowPlace> getNearest(GeoPosition geoPosition);
}
