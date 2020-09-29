package cityguide.datastorage.db;

import java.util.List;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cityguide.datastorage.model.GeoPosition;

public class MongoGeoDbController<T> extends MongoDbController<T> implements GeoDbController<T> {
    private static final Logger logger = LoggerFactory.getLogger(MongoGeoDbController.class);
    private static final double EARTH_RADIUS = 6371_000.0;

    @Override
    public void loadData(String dbName, String collectionName, Class<T> dataClass) {
        super.loadData(dbName, collectionName, dataClass);
        super.getDataCollection().createIndex(Indexes.geo2dsphere("location"));
    }

    public List<T> getNearest(GeoPosition geoPosition, double radiusInMeter) {
        logger.debug("get nearest for position {} and radius {}", geoPosition, radiusInMeter);
        double distanceInRad = radiusInMeter / EARTH_RADIUS;
        FindIterable<T> result = super.getDataCollection().find(Filters.geoWithinCenterSphere("location",
                geoPosition.getLatitude(), geoPosition.getLongitude(), distanceInRad));
        return toList(result);
    }
}
