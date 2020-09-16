package model;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class ShowPlace {
    private ObjectId id;
    private Address address;
    @BsonProperty(value = "address_string")
    private String addressString;
    private GeoPosition location;
    private List<Description> descriptionList = new ArrayList<>();

    public ShowPlace() {
    }

    public ObjectId getId() {
        return id;
    }

    public ShowPlace setId(final ObjectId id) {
        this.id = id;
        return this;
    }

    public Address getAdress() {
        return address;
    }

    public ShowPlace setAdress(Address adress) {
        this.address = adress;
        return this;
    }

    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    public ShowPlace setDescriptionList(List<Description> descriptionList) {
        this.descriptionList = descriptionList;
        return this;
    }

    public ShowPlace setAddressString(String addressString) {
        this.addressString = addressString;
        return this;
    }

    public String getAddressString() {
        return addressString;
    }

    public ShowPlace setLocation(GeoPosition location) {
        this.location = location;
        return this;
    }

    public GeoPosition getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "ShowPlace{" +
            "id = " + getId() +
            ", address = " + address +
            ", addressString = " + getAddressString() +
            ", location = " + getLocation() +
            ", descriptionList = " + getDescriptionList() +
            "}";
    }
}
