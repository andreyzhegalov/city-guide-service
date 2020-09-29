package cityguide.geocoder.service;

import java.util.List;

import cityguide.geocoder.dto.AddressDto;

public interface GeoCoderService {

    List<AddressDto> getAllAddresses();

    void fillCoordinate(AddressDto address);

    void sendAddress(AddressDto address);

    void fillAllAddresses();
}
