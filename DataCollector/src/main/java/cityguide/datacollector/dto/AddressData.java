package cityguide.datacollector.dto;

public class AddressData {
    private String address;
    private String info;

    public AddressData() {
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
        return "AddressData{" +
            "address = " + getAddress() +
            ", info = " + getInfo() +
            "}";
    }

}

