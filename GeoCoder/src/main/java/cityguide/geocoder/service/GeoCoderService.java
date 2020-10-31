package cityguide.geocoder.service;

import java.util.List;

import cityguide.geocoder.dto.AddressDto;

public interface GeoCoderService<T> {

    List<T> getAllAddresses();

    void fillCoordinate(AddressDto address);

    void sendAddress(AddressDto address);

    void fillAllAddresses();
}
