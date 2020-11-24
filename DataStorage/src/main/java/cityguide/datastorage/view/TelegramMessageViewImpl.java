package cityguide.datastorage.view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cityguide.datastorage.model.ShowPlace;

@Service
public class TelegramMessageViewImpl implements ShowPlaceView {

    @Override
    public String prepareMessage(List<ShowPlace> showPlaces) {
        if (showPlaces.isEmpty()) {
            return "Ничего не найдено";
        }
        final List<String> resultList = new ArrayList<String>();
        for (final var showPlace : showPlaces) {
            final var address = showPlace.getAddressString();
            final var descriptions = showPlace.getDescriptionList().stream().map(description -> {
                return address + System.lineSeparator() + description.getInfo();
            }).collect(Collectors.toList());
            resultList.addAll(descriptions);
        }
        return resultList.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}
