package cityguide.datastorage.service;

import java.util.List;

import cityguide.datastorage.model.ShowPlace;

public interface TelegramMessageService {

    String prepareMessage(List<ShowPlace> showPlaces);
}

