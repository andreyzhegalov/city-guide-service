package cityguide.datastorage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShowPlaceDto {
    private String address;
    private String info;
}
