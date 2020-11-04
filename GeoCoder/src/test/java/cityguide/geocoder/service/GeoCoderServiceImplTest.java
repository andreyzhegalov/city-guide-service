package cityguide.geocoder.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cityguide.geocoder.controller.DataStorageRestController;
import cityguide.geocoder.controller.GeoCoderRestController;
import cityguide.geocoder.dto.AddressDto;
import cityguide.geocoder.dto.Data;
import cityguide.geocoder.dto.Suggestion;

@ExtendWith(MockitoExtension.class)
class GeoCoderServiceImplTest {

    @Mock
    private DataStorageRestController<AddressDto> dataStorageRestController;

    @Mock
    private GeoCoderRestController geoCoderRestController;

    @Test
    void checkThatCoordinateAddedToAddressDto() {
        final var geoCoderService = new GeoCoderServiceImpl(geoCoderRestController, dataStorageRestController);

        final var address = new AddressDto();
        assertThat(address.getLatitude()).isZero();
        assertThat(address.getLongitude()).isZero();

        final var suggestion = new Suggestion();
        final var data = new Data();
        final double LATITUDE = 10.0;
        final double LONGITUDE = 20.0;
        data.setLatitude(LATITUDE);
        data.setLongitude(LONGITUDE);
        suggestion.setData(data);

        Mockito.when(geoCoderRestController.getSuggestions(null)).thenReturn(Arrays.asList(suggestion));

        geoCoderService.fillCoordinate(address);

        assertThat(address.getLatitude()).isEqualTo(LATITUDE);
        assertThat(address.getLongitude()).isEqualTo(LONGITUDE);
    }

    @Test
    void fillAllAddresses() {
        final var geoCoderService = new GeoCoderServiceImpl(geoCoderRestController, dataStorageRestController);
        final var addresses = Arrays.asList(new AddressDto(), new AddressDto());
        addresses.forEach(address -> address.setAddress("address"));
        Mockito.when(dataStorageRestController.getAddresses()).thenReturn(addresses);

        geoCoderService.fillAllAddresses();

        Mockito.verify(geoCoderRestController, times(addresses.size())).getSuggestions(anyString());
        Mockito.verify(dataStorageRestController, times(addresses.size())).sendAddress(any());
    }
}
