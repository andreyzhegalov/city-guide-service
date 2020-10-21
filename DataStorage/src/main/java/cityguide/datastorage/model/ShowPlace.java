package cityguide.datastorage.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ShowPlace {
    private ObjectId id;
    private Address address;
    @BsonProperty(value = "address_string")
    private String addressString;
    private Location location;
    @BsonProperty(value = "description_list")
    private List<Description> descriptionList = new ArrayList<>();

    public boolean hasLocation(){
        return location != null;
    }
}
