package cityguide.geocoder.controller;

import java.util.List;

public interface DataStorageRestController<T> {
    List<T> getAddresses();

    void sendAddress(T addressDto);
}
