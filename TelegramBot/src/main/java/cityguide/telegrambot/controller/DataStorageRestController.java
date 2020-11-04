package cityguide.telegrambot.controller;

import java.util.Optional;

public interface DataStorageRestController{
    public Optional<String> getShowPlaceDescription(float latitude, float longitude, int searchRadius);
}
