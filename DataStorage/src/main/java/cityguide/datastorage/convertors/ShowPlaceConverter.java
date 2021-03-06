package cityguide.datastorage.convertors;

import java.util.ArrayList;
import java.util.List;

import cityguide.datastorage.dto.AddressDto;
import cityguide.datastorage.dto.ShowPlaceDto;
import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;

public class ShowPlaceConverter {
    public static ShowPlace toShowPlace(AddressDto addressDto) {
        final var showPlace = new ShowPlace();
        showPlace.setAddressString(addressDto.getAddress());

        if (addressDto.getLatitude() != null && addressDto.getLatitude() != null) {
            final var location = new Location(addressDto.getLatitude(), addressDto.getLongitude());
            showPlace.setLocation(location);
        }
        return showPlace;
    }

    public static ShowPlace toShowPlace(ShowPlaceDto showPlaceDto) {
        final var showPlace = new ShowPlace();
        showPlace.setAddressString(showPlaceDto.getAddress());

        if (showPlaceDto.getInfo() == null){
            return showPlace;
        }
        final var description = new Description();
        description.setInfo(showPlaceDto.getInfo());
        final List<Description> descriptions = new ArrayList<>();
        descriptions.add(description);
        showPlace.setDescriptionList(descriptions);
        return showPlace;
    }
}
