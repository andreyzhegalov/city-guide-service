package cityguide.datastorage;

import java.util.List;
import java.util.Optional;

import cityguide.datastorage.contoroller.DbController;
import cityguide.datastorage.model.Address;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;

public class DataStorageImpl implements DataStorage {
    private final DbController dbController;

    public DataStorageImpl(DbController dbController) {
        this.dbController = dbController;
    }

    @Override
    public void insertUpdateData(ShowPlace showPlace) {
        dbController.insertUpdateData(showPlace);
    }

    @Override
    public Optional<ShowPlace> getData(ShowPlace showPlace) {
        return dbController.getData(showPlace);
    }

    @Override
    public List<ShowPlace> getNearest(GeoPosition geoPosition, double radiusInMeter) {
        return dbController.getNearest(geoPosition, radiusInMeter);
    }

    @Override
    public List<ShowPlace> getAllData() {
        return dbController.getAllData();
    }

    @Override
    public void closeDb() {
        dbController.closeDb();
    }
}
