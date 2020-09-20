package cityguide.datastorage.model;

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

    public double getLatitude(){
        if (! hasCoordinate()){
            throw new ModelException("coordinates not defined");
        }
        return coordinates.get(0);
    }

    public double getLongitude(){
        if (! hasCoordinate()){
            throw new ModelException("coordinates not defined");
        }
        return coordinates.get(1);
    }

    private boolean hasCoordinate(){
        return coordinates.size() == 2;
    }

    @Override
    public String toString() {
        return "GeoPosition{" +
            "coordinates = " + getCoordinates() +
            "}";
    }

    public String getType() {
        return type;
    }
}
