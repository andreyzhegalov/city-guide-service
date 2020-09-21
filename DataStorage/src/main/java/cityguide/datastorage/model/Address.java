package cityguide.datastorage.model;

public class Address {
    private String city;
    private String house;
    private String street;
    private String block;

    public Address() {
    }

    public String getHouse() {
        return house;
    }

    public Address setHouse(String house) {
        this.house = house;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getBlock() {
        return block;
    }

    public Address setBlock(String block) {
        this.block = block;
        return this;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("city = ").append(getCity());
        sb.append(", house = ").append(getHouse());
        sb.append(", street = ").append(getStreet());
        sb.append(", block = ").append(getBlock());
        return sb.append("}").toString();
    }
}
