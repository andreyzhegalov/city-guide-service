package cityguide.geocoder.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AddressDto {
    private String address;
    private double latitude;
    private double longitude;
}

