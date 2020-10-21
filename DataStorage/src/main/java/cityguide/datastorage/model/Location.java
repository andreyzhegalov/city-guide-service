package cityguide.datastorage.model;

import java.util.Arrays;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

    public double getLatitude() {
        if (!hasCoordinate()) {
            throw new ModelException("coordinates not defined");
        }
        return coordinates.get(0);
    }

    public Location setLatitude(double latitude) {
        coordinates.set(0, latitude);
        return this;
    }

    public double getLongitude() {
        if (!hasCoordinate()) {
            throw new ModelException("coordinates not defined");
        }
        return coordinates.get(1);
    }

    public Location setLongitude(double longitude) {
        coordinates.set(1, longitude);
        return this;
    }

    private boolean hasCoordinate() {
        return coordinates.size() == 2;
    }
}
