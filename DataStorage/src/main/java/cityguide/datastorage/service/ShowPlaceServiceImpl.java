package cityguide.datastorage.service;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.stereotype.Service;

import cityguide.datastorage.DataStorageException;
import cityguide.datastorage.db.GeoDbController;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;


@Service
public class ShowPlaceServiceImpl implements ShowPlaceService {
    private final GeoDbController<ShowPlace> geoController;

    public ShowPlaceServiceImpl(GeoDbController<ShowPlace> geoController) {
        this.geoController = geoController;
    }

    @Override
    public void insertUpdateData(ShowPlace showPlace) {
        final var filter = new Document("address_string", showPlace.getAddressString());

        final var showPlaceList = geoController.getData(filter);
        if (showPlaceList.size() > 1) {
            throw new DataStorageException("More than one show place with adress: " + showPlace.getAddressString());
        }

        if (showPlaceList.isEmpty()) {
            geoController.insertData(showPlace);
            return;
        }
        geoController.updateData(showPlace, filter);
    }

    @Override
    public Optional<ShowPlace> getData(String address) {
        final var filter = new Document("address_string", address);
        final var showPlaceList = geoController.getData(filter);
        return (showPlaceList.size() > 0) ? Optional.of(showPlaceList.get(0)) : Optional.empty();
    }

    @Override
    public List<ShowPlace> getNearest(GeoPosition geoPosition, double radiusInMeter) {
        return geoController.getNearest(geoPosition, radiusInMeter);
    }

    @Override
    public List<ShowPlace> getAllData() {
        return geoController.getAllData();
    }

}
