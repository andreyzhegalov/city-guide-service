package cityguide.datastorage.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class ShowPlace {
    private ObjectId id;
    private Address address;
    @BsonProperty(value = "address_string")
    private String addressString;
    private Location location;
    @BsonProperty(value = "description_list")
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

    public Address getAddress() {
        return address;
    }

    public ShowPlace setAddress(Address address) {
        this.address = address;
        return this;
    }

    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    public ShowPlace setDescriptionList(List<Description> descriptionList) {
        this.descriptionList = descriptionList;
        return this;
    }

    public ShowPlace addDescription(Description newDescription) {
        this.descriptionList.add(newDescription);
        return this;
    }

    public ShowPlace setAddressString(String addressString) {
        this.addressString = addressString;
        return this;
    }

    public String getAddressString() {
        return addressString;
    }

    public ShowPlace setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public boolean hasLocation(){
        return location != null;
    }

    @Override
    public String toString() {
        return "ShowPlace{" + "id = " + getId() + ", address = " + address + ", addressString = " + getAddressString()
                + ", location = " + getLocation() + ", descriptionList = " + getDescriptionList() + "}";
    }
}
