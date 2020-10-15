package cityguide.datastorage.view;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.ShowPlace;

@Service
public class TelegramMessageViewImpl implements ShowPlaceView {

    @Override
    public String prepareMessage(List<ShowPlace> showPlaces) {
        // NOTE only first description at this time
        return (showPlaces.isEmpty()) ? "Ничего не найдено"
                : showPlaces.get(0).getDescriptionList().stream().map(Description::getInfo).collect(Collectors.toList())
                        .toString();
    }
}
