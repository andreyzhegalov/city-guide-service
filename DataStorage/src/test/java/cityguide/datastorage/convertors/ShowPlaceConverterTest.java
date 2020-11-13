package cityguide.datastorage.convertors;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import cityguide.datastorage.dto.AddressDto;
import cityguide.datastorage.dto.ShowPlaceDto;
import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;

class ShowPlaceConverterTest {

    @Test
    void testToShowPlaceFromAddressDtoForEmptyObject() {
        final var addressDto = new AddressDto();
        final var showPlace = new ShowPlace();
        assertThat(ShowPlaceConverter.toShowPlace(addressDto)).isEqualTo(showPlace);
    }

    @Test
    void testToShowPlaceFromAddressDto() {
        final var addressDto = new AddressDto();
        addressDto.setAddress("street");
        addressDto.setLatitude(1.0);
        addressDto.setLongitude(2.0);

        final var showPlace = new ShowPlace();
        showPlace.setAddressString("street");
        showPlace.setLocation(new Location(1.0, 2.0));

        assertThat(ShowPlaceConverter.toShowPlace(addressDto)).isEqualTo(showPlace);
    }

    @Test
    void testToShowPlaceFromShowPlaceDtoForEmptyObject() {
        final var showPlaceDto = new ShowPlaceDto();
        final var showPlace = new ShowPlace();
        assertThat(ShowPlaceConverter.toShowPlace(showPlaceDto)).isEqualTo(showPlace);
    }

    @Test
    void testToShowPlaceFromShowPlaceDto() {
        final var showPlaceDto = new ShowPlaceDto();
        showPlaceDto.setAddress("street");
        showPlaceDto.setInfo("info");

        final var showPlace = new ShowPlace();
        showPlace.setAddressString("street");
        final var description = new Description();
        description.setInfo("info");
        showPlace.setDescriptionList(Collections.singletonList(description));

        assertThat(ShowPlaceConverter.toShowPlace(showPlaceDto)).isEqualTo(showPlace);
    }
}
