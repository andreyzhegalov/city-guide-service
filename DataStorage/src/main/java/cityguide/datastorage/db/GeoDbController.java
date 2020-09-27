package cityguide.datastorage.db;

import java.util.List;

import cityguide.datastorage.model.GeoPosition;

public interface GeoDbController<T> extends DbController<T>{
    List<T> getNearest(GeoPosition geoPosition, double radiusInMeter);
}

