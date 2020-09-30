package cityguide.geocoder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
    private String region;
    private String city;
    private String street;
    private String house;
    private String block;
    private double latitude;
    private double longitude;

    public Data() {
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHouse() {
        return house;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBlock() {
        return block;
    }

    @JsonProperty("geo_lat")
    public void setLatitude(double geo_lat) {
        this.latitude = geo_lat;
    }

    public double getLatitude() {
        return latitude;
    }

    @JsonProperty("geo_lon")
    public void setLongitude(double geo_lon) {
        this.longitude = geo_lon;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Data{" + "region = " + getRegion() +
                ", city = " + getCity() +
                ", street = " + getStreet() +
                ", house = " + getHouse() +
                ", block = " + getBlock() +
                ", latitude = " + getLatitude() +
                ", longitude = " + getLongitude() +
                "}";
    }
}
