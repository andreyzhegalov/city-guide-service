package cityguide.datastorage.mongo.dao;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import cityguide.datastorage.core.dao.ShowPlaceDao;
import cityguide.datastorage.core.db.GeoDbController;
import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ShowPlaceDaoMongo implements ShowPlaceDao {
    private final GeoDbController<ShowPlace> geoController;

    public ShowPlaceDaoMongo(GeoDbController<ShowPlace> geoController) {
        this.geoController = geoController;
    }

    @Override
    public void insertUpdateShowplace(ShowPlace showPlace) {
        final var mayBeShowPlace = getShowPlace(showPlace.getAddressString());
        if (mayBeShowPlace.isEmpty()) {
            log.info("insert showPlace {}", showPlace);
            geoController.insertData(showPlace);
            return;
        }
        final var finalShowPlace = mergeShowPlace(mayBeShowPlace.get(), showPlace);
        log.info("update showPlace {}", finalShowPlace);
        final var filter = eq("address_string", finalShowPlace.getAddressString());
        geoController.updateData(finalShowPlace, filter);
    }

    @Override
    public Optional<ShowPlace> getShowPlace(String address) {
        final var showPlaceList = geoController.getData(eq("address_string", address));
        if (showPlaceList.size() > 1) {
            log.error("more than one show place with address: {}", address);
            throw new DaoMongoException("More than one show place with address: " + address);
        }
        return (showPlaceList.size() > 0) ? Optional.of(showPlaceList.get(0)) : Optional.empty();
    }

    @Override
    public List<ShowPlace> getNearest(Location location, double radiusInMeter) {
        return geoController.getNearest(location, radiusInMeter);
    }

    @Override
    public List<ShowPlace> getAllShowPlace() {
        return geoController.getAllData();
    }

    @Override
    public List<ShowPlace> getAllShowPlace(boolean hasCoordinate) {
        return geoController.getData(exists("location", false));
    }

    private ShowPlace mergeShowPlace(ShowPlace initShowPlace, ShowPlace newShowPlace) {
        if (initShowPlace.hasLocation()) {
            return initShowPlace;
        }
        if (newShowPlace.hasLocation()) {
            return newShowPlace;
        }
        // TODO add merge description logic
        return initShowPlace;
    }
}
