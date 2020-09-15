package model;

public class Adress {
    private final String house;
    private final String street;
    private final String block;

    public Adress(String house, String street, String block) {
        this.house = house;
        this.street = street;
        this.block = block;
    }

    public String getHouse() {
        return house;
    }

    public String getStreet() {
        return street;
    }

    public String getBlock() {
        return block;
    }

    @Override
    public String toString() {
        return "Adress{" +
            "house = " + getHouse() +
            ", street = " + getStreet() +
            ", block = " + getBlock() +
            "}";
    }

}



