package cityguide.datastorage.dto;

public class ShowPlaceDto {
    private String address;
    private String info;

    public ShowPlaceDto() {
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "ShowPlaceDto{" +
            "address = " + getAddress() +
            ", info = " + getInfo() +
            "}";
    }
}
