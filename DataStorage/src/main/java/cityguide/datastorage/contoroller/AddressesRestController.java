package cityguide.datastorage.contoroller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cityguide.datastorage.model.ShowPlace;
import cityguide.datastorage.service.ShowPlaceService;

@RestController
public class AddressesRestController {
    private static final Logger logger = LoggerFactory.getLogger(AddressesRestController.class);
    private final ShowPlaceService showPlaceService;

    public AddressesRestController(ShowPlaceService showPlaceService) {
        this.showPlaceService = showPlaceService;
    }

    @RequestMapping(value = { "/api/addresses" }, params = {
            "hascoord" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<String> getShowplaceAddresses(@RequestParam(name = "hascoord") boolean hasCoordinate) {
        final var showPlaceList = showPlaceService.getAllShowPlace(false);
        final var addressList = showPlaceList.stream().map(ShowPlace::getAddressString).collect(Collectors.toList());
        return addressList;
    }
}

