package cityguide.geocoder.dto;

public class Data {
    private String region;
    private String city;
    private String street;
    private String house;
    private String block;
    private double geo_lat;
    private double geo_lon;

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

    public void setGeo_lat(double geo_lat) {
        this.geo_lat = geo_lat;
    }

    public double getGeo_lat() {
        return geo_lat;
    }

    public void setGeo_lon(double geo_lon) {
        this.geo_lon = geo_lon;
    }

    public double getGeo_lon() {
        return geo_lon;
    }

    @Override
    public String toString() {
        return "Data{" + "region = " + getRegion() + ", city = " + getCity() + ", street = " + getStreet()
                + ", house = " + getHouse() + ", block = " + getBlock() + ", geo_lat = " + getGeo_lat() + ", geo_lon = "
                + getGeo_lon() + "}";
    }
}

