package cityguide.geocoder.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cityguide.geocoder.controller.DataStorageRestController;
import cityguide.geocoder.controller.GeoCoderRestController;
import cityguide.geocoder.dto.AddressDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public final class GeoCoderServiceImpl implements GeoCoderService<AddressDto> {
    private final GeoCoderRestController geoCoderRestController;
    private final DataStorageRestController<AddressDto> dataStorageRestController;

    public GeoCoderServiceImpl(GeoCoderRestController geoCoderRestController,
            DataStorageRestController<AddressDto> dataStorageRestController) {
        this.geoCoderRestController = Objects.requireNonNull(geoCoderRestController);
        this.dataStorageRestController = Objects.requireNonNull(dataStorageRestController);
    }

    @Override
    public void fillCoordinate(AddressDto address) {
        final var suggestionList = geoCoderRestController.getSuggestions(address.getAddress());
        if (suggestionList.isEmpty()) {
            return;
        }
        final var suggestion = suggestionList.get(0);
        address.setLatitude(suggestion.getData().getLatitude());
        address.setLongitude(suggestion.getData().getLongitude());
    }

    @Override
    public void fillAllAddresses() {
        getAllAddresses().forEach(address -> {
            fillCoordinate(address);
            sendAddress(address);
            sleep();
        });
    }

    private void sendAddress(AddressDto address) {
        dataStorageRestController.sendAddress(address);
    }

    private List<AddressDto> getAllAddresses() {
        return dataStorageRestController.getAddresses();
    }

    private void sleep() {
        try {
            Thread.sleep(getRandomNumber(1_000, 3 * 1_000));
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
