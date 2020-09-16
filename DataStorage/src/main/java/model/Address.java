package model;

public class Address {
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

    @Override
    public String toString() {
        return "Adress{" + "house = " + getHouse() + ", street = " + getStreet() + ", block = " + getBlock() + "}";
    }

}
