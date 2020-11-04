package cityguide.geocoder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Data {
    private String region;
    private String city;
    private String street;
    private String house;
    private String block;
    @JsonProperty("geo_lat")
    private double latitude;
    @JsonProperty("geo_lon")
    private double longitude;
}
