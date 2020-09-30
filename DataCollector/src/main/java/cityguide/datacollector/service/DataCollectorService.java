package cityguide.datacollector.service;

import cityguide.datacollector.dto.AddressDto;

public interface DataCollectorService {
    void start();

    void sendData(AddressDto addressData);
}
