package cityguide.datastorage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDto {
    private String address;
    private Double latitude;
    private Double longitude;
}

