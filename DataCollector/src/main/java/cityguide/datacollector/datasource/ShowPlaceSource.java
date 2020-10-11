package cityguide.datacollector.datasource;

import java.util.function.Consumer;

import cityguide.datacollector.dto.ShowPlaceDto;

public interface ShowPlaceSource {

    void collect();

    void setHandler(Consumer<ShowPlaceDto> newShowPlaceHandler);
}

