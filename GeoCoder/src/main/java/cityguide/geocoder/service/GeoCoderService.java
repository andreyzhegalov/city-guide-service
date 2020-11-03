package cityguide.geocoder.service;

public interface GeoCoderService<T> {
    void fillCoordinate(T address);

    void fillAllAddresses();
}
