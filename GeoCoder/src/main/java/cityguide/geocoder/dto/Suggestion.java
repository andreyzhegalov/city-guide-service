package cityguide.geocoder.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Suggestion {
    private String value;
    private Data data;
}

