package cityguide.geocoder.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cityguide.geocoder.controller.DataStorageRestController;
import cityguide.geocoder.controller.GeoCoderRestController;
import cityguide.geocoder.dto.AddressDto;

@Service
public class GeoCoderServiceImpl implements GeoCoderService {
    private final static Logger logger = LoggerFactory.getLogger(GeoCoderServiceImpl.class);
    private final GeoCoderRestController geoCoderRestController;
    private final DataStorageRestController dataStorageRestController;

    public GeoCoderServiceImpl(GeoCoderRestController geoCoderRestController,
            DataStorageRestController dataStorageRestController) {
        this.geoCoderRestController = geoCoderRestController;
        this.dataStorageRestController = dataStorageRestController;
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        return dataStorageRestController.getAddresses();
    }

    @Override
    public void fillCoordinate(AddressDto address) {
        final var suggestionList = geoCoderRestController.getSuggestions(address.getAddress());
        if (suggestionList.getSuggestions().isEmpty()) {
            return;
        }

        final var suggestion = suggestionList.getSuggestions().get(0);
        address.setLatitude(suggestion.getData().getLatitude());
        address.setLongitude(suggestion.getData().getLongitude());
    }

    @Override
    public void sendAddress(AddressDto address) {
        dataStorageRestController.sendAddress(address);
    }

    @Override
    public void fillAllAddresses() {
        final var addressesList = getAllAddresses();
        for (var address : addressesList) {
            fillCoordinate(address);
            sendAddress(address);
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(getRandomNumber(1_000, 3 * 1_000));
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
