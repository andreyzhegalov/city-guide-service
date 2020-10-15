package cityguide.datastorage.view;

import java.util.List;

import cityguide.datastorage.model.ShowPlace;

public interface ShowPlaceView {

    String prepareMessage(List<ShowPlace> showPlaces);

}

