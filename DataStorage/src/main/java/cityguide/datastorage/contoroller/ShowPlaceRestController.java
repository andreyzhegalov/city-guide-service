    package cityguide.datastorage.contoroller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cityguide.datastorage.convertors.ShowPlaceConverter;
import cityguide.datastorage.core.dao.ShowPlaceDao;
import cityguide.datastorage.dto.ShowPlaceDto;
import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;
import cityguide.datastorage.view.ShowPlaceView;

@RestController
public class ShowPlaceRestController {
    private final ShowPlaceDao showPlaceService;
    private final ShowPlaceView telegramMessageService;

    public ShowPlaceRestController(ShowPlaceDao showPlaceService, ShowPlaceView telegramMessageService) {
        this.showPlaceService = showPlaceService;
        this.telegramMessageService = telegramMessageService;
    }

    @RequestMapping(
        value = { "/api/showplaces" },
        params = { "lat", "lon", "radius" },
        method = RequestMethod.GET,
        produces = "application/json;charset=UTF-8"
    )
    public String getShowplace(@RequestParam(name = "lat") Double latitude,
            @RequestParam(name = "lon") Double longitude,
            @RequestParam(name = "radius", defaultValue = "100") int searchRadius) {
        final Location location = new Location(latitude, longitude);
        final List<ShowPlace> showPlaces = showPlaceService.getNearest(location, searchRadius);
        return telegramMessageService.prepareMessage(showPlaces);
    }

    @RequestMapping(
        value = { "/api/showplaces" },
        params = { "address" },
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public void postShowplace(@RequestParam(name = "address") String address, @RequestBody ShowPlaceDto newShowPlace) {
        showPlaceService.insertUpdateShowplace(ShowPlaceConverter.toShowPlace(newShowPlace));
    }
}
