package cityguide.datastorage.contoroller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cityguide.datastorage.core.service.ShowPlaceService;
import cityguide.datastorage.dto.AddressDto;
import cityguide.datastorage.model.Location;

@RestController
public class AddressesRestController {
    private final ShowPlaceService showPlaceService;

    public AddressesRestController(ShowPlaceService showPlaceService) {
        this.showPlaceService = showPlaceService;
    }

    @RequestMapping(value = { "/api/addresses" }, params = {
            "hascoord" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<AddressDto> getAddresses(@RequestParam(name = "hascoord") boolean hasCoordinate) {
        final var showPlaceList = showPlaceService.getShowPlaces(false);

        return showPlaceList.stream().map(showPlace -> {
            final var addressDto = new AddressDto();
            addressDto.setAddress(showPlace.getAddressString());
            return addressDto;
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = {
            "/api/addresses" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void postAddress(@RequestBody AddressDto newAddressData) {
        showPlaceService.updateLocation(newAddressData.getAddress(),
                new Location(newAddressData.getLatitude(), newAddressData.getLongitude()));
    }
}
