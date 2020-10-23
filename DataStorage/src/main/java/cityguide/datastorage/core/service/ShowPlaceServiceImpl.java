package cityguide.datastorage.core.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cityguide.datastorage.core.dao.ShowPlaceDao;
import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;
import cityguide.datastorage.view.ShowPlaceView;

@Service
public class ShowPlaceServiceImpl implements ShowPlaceService {
    private final ShowPlaceDao showPlaceDao;
    private final ShowPlaceView showPlaceView;

    public ShowPlaceServiceImpl(ShowPlaceDao showPlaceDao, ShowPlaceView showPlaceView) {
        this.showPlaceDao = showPlaceDao;
        this.showPlaceView = showPlaceView;
    }

    public String getDescription(Location location, double searchRadius) {
        final List<ShowPlace> showPlaces = showPlaceDao.getNearest(location, searchRadius);
        return showPlaceView.prepareMessage(showPlaces);
    }

    @Override
    public void insertUpdateShowplace(ShowPlace showPlace) {
        showPlaceDao.insertUpdateShowplace(showPlace);
    }

    @Override
    public void updateLocation(String address, Location location) {
        final var mayBeShowPlace = showPlaceDao.getShowPlace(address);
        if (mayBeShowPlace.isEmpty()) {
            return;
        }
        final var showPlace = mayBeShowPlace.get();
        showPlace.setLocation(location);
        showPlaceDao.insertUpdateShowplace(showPlace);
    }

    @Override
    public List<ShowPlace> getShowPlaces(boolean withCoord) {
        return showPlaceDao.getAllShowPlace(withCoord);
    }
}
