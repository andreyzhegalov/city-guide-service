package cityguide.datastorage.dto;

import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.ShowPlace;

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
    public ShowPlace toShowPlace(){
        final var showPlace = new ShowPlace();
        showPlace.setAddressString(getAddress());
        final Description description = new Description().setInfo(getInfo());
        final var descriptions = showPlace.getDescriptionList();
        descriptions.add(description);
        showPlace.setDescriptionList(descriptions);
        return showPlace;
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

