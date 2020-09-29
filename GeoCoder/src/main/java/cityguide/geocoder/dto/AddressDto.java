package cityguide.geocoder.dto;

public class AddressDto {
    private String address;
    private Double latitude;
    private Double longitude;

    public AddressDto() {
    }

    public AddressDto setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "AddressData{" +
            "address = " + getAddress() +
            ", latitude = " + getLatitude() +
            ", longitude = " + getLongitude() +
            "}";
    }
}

