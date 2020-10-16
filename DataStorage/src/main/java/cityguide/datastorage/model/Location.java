package cityguide.datastorage.model;

import java.util.Arrays;
import java.util.List;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Location {
    private List<Double> coordinates;
    private String type = "Point";

    public Location(){
        coordinates = Arrays.asList(new Double[2]);
    }

    public Location(Double latitude, Double longitude){
        this.coordinates = Arrays.asList(latitude, longitude);
    }

    public Location setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public Location setType(String type) {
        this.type = type;
        return this;
    }

    public double getLatitude() {
        if (noCoordinate()) {
            throw new ModelException("coordinates not defined");
        }
        return coordinates.get(0);
    }

    public Location setLatitude(double latitude) {
        coordinates.set(0, latitude);
        return this;
    }

    public double getLongitude() {
        if (noCoordinate()) {
            throw new ModelException("coordinates not defined");
        }
        return coordinates.get(1);
    }

    public Location setLongitude(double longitude) {
        coordinates.set(1, longitude);
        return this;
    }

    private boolean noCoordinate() {
        return coordinates.size() != 2;
    }

    @Override
    public String toString() {
        return "GeoPosition{" + "coordinates = " + getCoordinates() + "}";
    }

    public String getType() {
        return type;
    }
}
