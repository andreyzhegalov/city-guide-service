package cityguide.datastorage;

import java.util.List;
import java.util.Optional;

import org.bson.Document;

import cityguide.datastorage.contoroller.GeoDbController;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;

public class DataStorageImpl implements DataStorage {
    private final GeoDbController<ShowPlace> geoController;

    public DataStorageImpl(GeoDbController<ShowPlace> geoController) {
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

    @Override
    public void closeDb() {
        geoController.closeDb();
    }
}
