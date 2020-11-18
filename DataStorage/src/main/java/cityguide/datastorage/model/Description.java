package cityguide.datastorage.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Description {
    private String title;
    private String yearOfConstruction;
    private String architect;
    private String info;
}
