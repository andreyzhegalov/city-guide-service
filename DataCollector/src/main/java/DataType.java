import java.util.List;

public class DataType {
    private List<String> addressList;
    private String description;

    public void setAddress(List<String> addressList) {
        this.addressList = addressList;
    }

    public List<String> getAddress() {
        return addressList;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "DataType{" + "addressList = " + addressList + ", description = " + getDescription() + "}";
    }

}
