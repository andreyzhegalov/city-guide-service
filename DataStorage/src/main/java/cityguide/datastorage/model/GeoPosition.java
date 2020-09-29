package cityguide.datastorage.model;

import java.util.Arrays;
import java.util.List;

public class GeoPosition {
    private List<Double> coordinates;
    private String type = "Point";

    public GeoPosition(){
        coordinates = Arrays.asList(new Double[2]);
    }

    public GeoPosition setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public GeoPosition setType(String type) {
        this.type = type;
        return this;
    }

    public double getLatitude() {
        if (!hasCoordinate()) {
            throw new ModelException("coordinates not defined");
        }
        return coordinates.get(0);
    }

    public GeoPosition setLatitude(double latitude) {
        coordinates.set(0, latitude);
        return this;
    }

    public double getLongitude() {
        if (!hasCoordinate()) {
            throw new ModelException("coordinates not defined");
        }
        return coordinates.get(1);
    }

    public GeoPosition setLongitude(double longitude) {
        coordinates.set(1, longitude);
        return this;
    }

    private boolean hasCoordinate() {
        return coordinates.size() == 2;
    }

    @Override
    public String toString() {
        return "GeoPosition{" + "coordinates = " + getCoordinates() + "}";
    }

    public String getType() {
        return type;
    }
}
