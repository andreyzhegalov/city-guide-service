package cityguide.datacollector.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cityguide.datacollector.controller.ShowPlaceCollectorRestController;
import cityguide.datacollector.datasource.ShowPlaceSource;
import cityguide.datacollector.dto.ShowPlaceDto;

@Service
public class ShowPlaceCollectorServiceImpl implements ShowPlaceCollectorService {
    private final ShowPlaceCollectorRestController restController;
    private final List<ShowPlaceSource> showPlaceSources = new ArrayList<>();

    public ShowPlaceCollectorServiceImpl(ShowPlaceCollectorRestController restController) {
        this.restController = restController;
    }

    @Override
    public void start() {
        showPlaceSources.forEach(ShowPlaceSource::collect);
    }

    @Override
    public synchronized void send(ShowPlaceDto addressData) {
        restController.sendPost(addressData);
        notifyAll();
    }

    @Override
    public void addShowPlaceSource(ShowPlaceSource newSource) {
        newSource.setHandler(this::send);
        showPlaceSources.add(newSource);
    }
}
