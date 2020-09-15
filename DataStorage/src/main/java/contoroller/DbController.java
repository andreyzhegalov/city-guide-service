package contoroller;

import java.util.List;

import model.Adress;
import model.GeoPosition;
import model.ShowPlace;

public interface DbController {
    void setData(ShowPlace showPlace);

    List<ShowPlace> getData(Adress adress);

    List<ShowPlace> getNearest(GeoPosition geoPosition);
}
