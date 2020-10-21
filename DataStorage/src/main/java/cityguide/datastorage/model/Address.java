package cityguide.datastorage.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Address {
    private String city;
    private String house;
    private String street;
    private String block;
}
