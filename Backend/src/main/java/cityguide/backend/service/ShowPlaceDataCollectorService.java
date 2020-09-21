package cityguide.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cityguide.datacollector.DataCollector;
import cityguide.datacollector.DataType;
import cityguide.datastorage.DataStorage;
import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.ShowPlace;

@Service
public class ShowPlaceDataCollectorService implements DataCollectorService {
    private final static Logger logger = LoggerFactory.getLogger(ShowPlaceDataCollectorService.class);
    final DataStorage dataStorage;
    final DataCollector dataCollector;

    public ShowPlaceDataCollectorService(DataStorage dataStorage, DataCollector dataCollector) {
        this.dataStorage = dataStorage;
        this.dataCollector = dataCollector;
    }

    @Override
    public void loadData() {
        dataCollector.setCallback((data) -> {
            logger.debug("recived data from DataCollector for address : {}", data.getAddress());
            convert(data).forEach(showPlace -> {
                dataStorage.insertUpdateData(showPlace);
            });
        });
        dataCollector.start();
    }

    private List<ShowPlace> convert(DataType data) {
        final var showPlaceList = new ArrayList<ShowPlace>();
        final var description = new Description().setInfo(data.getDescription());
        data.getAddress().forEach(address -> {
            final var showPlace = new ShowPlace();
            showPlace.addDescription(description);
            showPlace.setAddressString(address);
            showPlaceList.add(showPlace);
        });
        return showPlaceList;
    }
}
