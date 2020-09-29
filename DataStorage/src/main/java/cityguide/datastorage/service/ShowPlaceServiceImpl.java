package cityguide.datastorage.service;

import static com.mongodb.client.model.Filters.exists;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cityguide.datastorage.db.GeoDbController;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;

@Service
public class ShowPlaceServiceImpl implements ShowPlaceService {
    private final static Logger logger = LoggerFactory.getLogger(ShowPlaceServiceImpl.class);
    private final GeoDbController<ShowPlace> geoController;

    public ShowPlaceServiceImpl(GeoDbController<ShowPlace> geoController) {
        this.geoController = geoController;
    }

    @Override
    public void insertUpdateShowplace(ShowPlace showPlace) {
        final var filter = new Document("address_string", showPlace.getAddressString());
        logger.info(" insertUpdateShowplace {}", showPlace);

        final var showPlaceList = geoController.getData(filter);
        if (showPlaceList.size() > 1) {
            throw new ShowPlaceServiceException("More than one show place with adress: " + showPlace.getAddressString());
        }

        if (showPlaceList.isEmpty()) {
            logger.info("insert new showPlace {}", showPlace);
            geoController.insertData(showPlace);
            return;
        }
        final var finalShowPlace =  mergeShowPlace(showPlace, showPlaceList.get(0));
        logger.info("update showPlace {}", finalShowPlace );
        geoController.updateData(finalShowPlace, filter);
    }

    @Override
    public Optional<ShowPlace> getShowPlace(String address) {
        final var filter = new Document("address_string", address);
        final var showPlaceList = geoController.getData(filter);
        return (showPlaceList.size() > 0) ? Optional.of(showPlaceList.get(0)) : Optional.empty();
    }

    @Override
    public List<ShowPlace> getNearest(GeoPosition geoPosition, double radiusInMeter) {
        return geoController.getNearest(geoPosition, radiusInMeter);
    }

    @Override
    public List<ShowPlace> getAllShowPlace() {
        return geoController.getAllData();
    }

    @Override
    public List<ShowPlace> getAllShowPlace(boolean hasCoordinate) {
        return geoController.getData(exists("location", false));
    }

    private ShowPlace mergeShowPlace(ShowPlace initShowPlace, ShowPlace newShowPlace){
        if (initShowPlace.hasLocation()){
            return initShowPlace;
        }
        if (newShowPlace.hasLocation()) {
            return newShowPlace;
        }
        // TODO add merge description logic
        return initShowPlace;
    }
}
