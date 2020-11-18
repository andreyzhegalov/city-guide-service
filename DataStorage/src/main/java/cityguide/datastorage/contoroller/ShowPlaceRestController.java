package cityguide.datastorage.contoroller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cityguide.datastorage.convertors.ShowPlaceConverter;
import cityguide.datastorage.core.service.ShowPlaceService;
import cityguide.datastorage.dto.ShowPlaceDto;
import cityguide.datastorage.model.Location;

@RestController
public class ShowPlaceRestController {
    private final ShowPlaceService showPlaceService;

    public ShowPlaceRestController(ShowPlaceService showPlaceService) {
        this.showPlaceService = showPlaceService;
    }

    @RequestMapping(value = { "/api/showplaces" }, params = { "lat", "lon",
            "radius" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getShowplace(@RequestParam(name = "lat") Double latitude,
            @RequestParam(name = "lon") Double longitude,
            @RequestParam(name = "radius", defaultValue = "100") int searchRadius) {

        final Location location = new Location(latitude, longitude);
        return showPlaceService.getDescription(location, searchRadius);
    }

    @RequestMapping(value = { "/api/showplaces" }, params = {
            "address" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void postShowplace(@RequestParam(name = "address") String address, @RequestBody ShowPlaceDto newShowPlace) {
        showPlaceService.insertUpdateShowplace(ShowPlaceConverter.toShowPlace(newShowPlace));
    }
}
