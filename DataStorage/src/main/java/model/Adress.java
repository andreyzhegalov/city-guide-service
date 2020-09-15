package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Adress {
    private String house;
    private String street;
    private String block;

    public Adress(){
    }

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

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return "Adress{" + "house = " + getHouse() + ", street = " + getStreet() + ", block = " + getBlock() + "}";
    }

}
