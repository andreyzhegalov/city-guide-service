package contoroller;

import model.Adress;
import model.GeoPosition;
import model.ShowPlace;

public interface DbController {
    void setData(ShowPlace showPlace);

    ShowPlace getData(Adress adress);

    ShowPlace getNearest(GeoPosition geoPosition);
}
