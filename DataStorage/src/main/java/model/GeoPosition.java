package model;

public class GeoPosition {
    private final float lat;
    private final float lon;

    public GeoPosition(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }
}
