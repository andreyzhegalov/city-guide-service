package cityguide.geocoder.dto;

public class Data {
    private String region;
    private String city;
    private String street;
    private String house;
    private String block;
    private float geo_lat;
    private float geo_lon;

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

    public void setGeo_lat(float geo_lat) {
        this.geo_lat = geo_lat;
    }

    public float getGeo_lat() {
        return geo_lat;
    }

    public void setGeo_lon(float geo_lon) {
        this.geo_lon = geo_lon;
    }

    public float getGeo_lon() {
        return geo_lon;
    }

    @Override
    public String toString() {
        return "Data{" + "region = " + getRegion() + ", city = " + getCity() + ", street = " + getStreet()
                + ", house = " + getHouse() + ", block = " + getBlock() + ", geo_lat = " + getGeo_lat() + ", geo_lon = "
                + getGeo_lon() + "}";
    }
}

