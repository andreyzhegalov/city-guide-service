package cityguide.datacollector.showplacesource;

import java.util.function.Consumer;

import cityguide.datacollector.dto.ShowPlaceDto;

public interface ShowPlaceSource {

    void collect();

    void setHandler(Consumer<ShowPlaceDto> newShowPlaceHandler);
}

