package model;

import java.util.ArrayList;
import java.util.List;

public class GeoPosition {
    private List<Double> coordinates = new ArrayList<>();
    private String type = "Point";

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

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "GeoPosition{" +
            "coordinates = " + getCoordinates() +
            ", type = " + getType() +
            "}";
    }
}
