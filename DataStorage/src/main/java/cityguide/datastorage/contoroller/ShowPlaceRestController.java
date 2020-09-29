package cityguide.datastorage.contoroller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cityguide.datastorage.convertors.ShowPlaceConvertor;
import cityguide.datastorage.dto.ShowPlaceDto;
import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;
import cityguide.datastorage.service.ShowPlaceService;

@RestController
public class ShowPlaceRestController {
    private static final Logger logger = LoggerFactory.getLogger(ShowPlaceRestController.class);
    private final ShowPlaceService showPlaceService;

    public ShowPlaceRestController(ShowPlaceService showPlaceService) {
        this.showPlaceService = showPlaceService;
    }

    @RequestMapping(value = { "/api/showplaces" }, params = { "lat", "lon",
            "radius" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getShowplace(@RequestParam(name = "lat") Double latiude, @RequestParam(name = "lon") Double longitude,
            @RequestParam(name = "radius", defaultValue = "100") int searchRadius) {
        logger.info("GET /api/showplaces with  lat = {} lon = {}", latiude, longitude);

        final GeoPosition geoPosition = new GeoPosition().setLatitude(latiude).setLongitude(longitude);
        final List<ShowPlace> showPlaces = showPlaceService.getNearest(geoPosition, searchRadius);
        final String responseMessage = makeResponseMessage(showPlaces);

        logger.info(" GET /api/showplaces with  lat = {} lon = {}", latiude, longitude);
        return responseMessage;
    }

    @RequestMapping(value = { "/api/showplaces" }, params = {
            "address" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void postShowplace(@RequestParam(name = "address") String address, @RequestBody ShowPlaceDto newShowPlace) {
        logger.info("recive new address data {}", newShowPlace);
        showPlaceService.insertUpdateShowplace(ShowPlaceConvertor.toShowPlace(newShowPlace));
    }

    private String makeResponseMessage(List<ShowPlace> showPlaces) {
        // NOTE only first description at this time
        return (showPlaces.isEmpty()) ? "Ничего не найдено"
                : showPlaces.get(0).getDescriptionList().stream().map(Description::getInfo).collect(Collectors.toList())
                        .toString();
    }
}
