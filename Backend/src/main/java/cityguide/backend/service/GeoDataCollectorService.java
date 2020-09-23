package cityguide.backend.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cityguide.datastorage.DataStorage;
import cityguide.datastorage.model.Address;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;
import cityguide.geocoder.GeoCoder;
import cityguide.geocoder.dto.Data;

@Service
public class GeoDataCollectorService implements DataCollectorService {
    private final static Logger logger = LoggerFactory.getLogger(GeoDataCollectorService.class);
    private final DataStorage dataStorage;
    private final GeoCoder geoCoder;

    public GeoDataCollectorService(DataStorage dataStorage, GeoCoder geoCoder) {
        this.dataStorage = dataStorage;
        this.geoCoder = geoCoder;
    }

    @Override
    public void loadData() {
        final var allShowPlace = dataStorage.getAllData();
        for (final var showPlace : allShowPlace) {
            final var suggestionList = geoCoder.getSuggestions(showPlace.getAddressString());
            logger.debug("Recived suggestions {}", suggestionList);
            if (suggestionList.getSuggestions().isEmpty()) {
                continue;
            }
            final var data = suggestionList.getSuggestions().get(0).getData();
            fillShowPlace(showPlace, data);
            dataStorage.insertUpdateData(showPlace);
            sleep();
        }
    }

    private void fillShowPlace(ShowPlace showPlace, Data data) {
        final GeoPosition geoPosition = new GeoPosition().setLatitude(data.getLatitude())
                .setLongitude(data.getLongitude());
        showPlace.setLocation(geoPosition);
        showPlace.setAdress(new Address().setCity(data.getCity()).setStreet(data.getStreet()).setHouse(data.getHouse())
                .setBlock(data.getBlock()));
    }

    private void sleep() {
        try {
            Thread.sleep(getRandomNumber(1 * 1_000, 3 * 1_000));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
