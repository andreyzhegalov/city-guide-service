package cityguide.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CityGuideRestController {
    private static final Logger logger = LoggerFactory.getLogger(CityGuideRestController.class);

    public Optional<String> sendPost(Float latitude, Float longitude, int searchRadius){
        logger.info("invoked sendPost lat {} lon {} searchRadius {}", latitude, latitude, searchRadius);
        return Optional.empty();
    }
}

