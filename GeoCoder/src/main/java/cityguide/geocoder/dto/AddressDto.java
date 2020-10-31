package cityguide.geocoder.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Setter
public class AddressDto {
    private String address;
    private Double latitude;
    private Double longitude;
}

