package cityguide.datastorage.contoroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cityguide.datastorage.dto.AddressDto;
import cityguide.datastorage.model.Location;
import cityguide.datastorage.service.ShowPlaceService;

@RestController
public class AddressesRestController {
    private final ShowPlaceService showPlaceService;

    public AddressesRestController(ShowPlaceService showPlaceService) {
        this.showPlaceService = showPlaceService;
    }

    @RequestMapping(
        value = { "/api/addresses" },
        params = { "hascoord" },
        method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8"
    )
    public List<AddressDto> getAddresses(@RequestParam(name = "hascoord") boolean hasCoordinate) {
        final var showPlaceList = showPlaceService.getAllShowPlace(false);

        return showPlaceList.stream().map(showPlace -> {
            return new AddressDto().setAddress(showPlace.getAddressString());
        }).collect(Collectors.toList());
    }

    @RequestMapping(
        value = { "/api/addresses" },
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public void postAddress(@RequestBody AddressDto newAddressData) {
        final var mayBeShowPlace = showPlaceService.getShowPlace(newAddressData.getAddress());
        if( mayBeShowPlace.isEmpty())
        {
            return;
        }
        final var showPlace = mayBeShowPlace.get();
        final var location = new Location(newAddressData.getLatitude(), newAddressData.getLongitude());
        showPlace.setLocation(location);
        showPlaceService.insertUpdateShowplace(showPlace);
    }
}
