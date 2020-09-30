package cityguide.datastorage.db;

import java.util.List;

import cityguide.datastorage.model.Location;

public interface GeoDbController<T> extends DbController<T>{
    List<T> getNearest(Location location, double radiusInMeter);
}

