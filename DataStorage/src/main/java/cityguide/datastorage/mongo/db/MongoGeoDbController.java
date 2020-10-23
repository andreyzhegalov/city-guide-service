package cityguide.datastorage.mongo.db;

import java.util.List;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;

import org.springframework.stereotype.Controller;

import cityguide.datastorage.core.db.GeoDbController;
import cityguide.datastorage.model.Location;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MongoGeoDbController<T> extends MongoDbController<T> implements GeoDbController<T> {
    private static final double EARTH_RADIUS = 6371_000.0;

    public MongoGeoDbController(MongoClient mongoClient) {
        super(mongoClient);
    }

    @Override
    public void loadData(String dbName, String collectionName, Class<T> dataClass) {
        super.loadData(dbName, collectionName, dataClass);
        super.getDataCollection().createIndex(Indexes.geo2dsphere("location"));
    }

    public List<T> getNearest(Location location, double radiusInMeter) {
        log.debug("get nearest for position {} and radius {}", location, radiusInMeter);
        double distanceInRad = radiusInMeter / EARTH_RADIUS;
        FindIterable<T> result = super.getDataCollection().find(Filters.geoWithinCenterSphere("location",
                location.getLatitude(), location.getLongitude(), distanceInRad));
        return toList(result);
    }
}
