package cityguide.datacollector.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ShowPlaceDto {
    private String address;
    private String info;
}

