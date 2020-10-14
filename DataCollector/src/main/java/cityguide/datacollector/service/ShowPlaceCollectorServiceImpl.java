package cityguide.datacollector.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cityguide.datacollector.controller.ShowPlaceSendController;
import cityguide.datacollector.dto.ShowPlaceDto;
import cityguide.datacollector.showplacesource.ShowPlaceSource;

@Service
public class ShowPlaceCollectorServiceImpl implements ShowPlaceCollectorService {
    private final ShowPlaceSendController restController;
    private final List<ShowPlaceSource> showPlaceSources = new ArrayList<>();

    public ShowPlaceCollectorServiceImpl(ShowPlaceSendController restController) {
        this.restController = restController;
    }

    @Override
    public void start() {
        showPlaceSources.forEach(ShowPlaceSource::collect);
    }

    @Override
    public synchronized void send(ShowPlaceDto addressData) {
        restController.send(addressData);
        notifyAll();
    }

    @Override
    public void addShowPlaceSource(ShowPlaceSource newSource) {
        newSource.setReceiver(this::send);
        showPlaceSources.add(newSource);
    }
}
