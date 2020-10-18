package cityguide.datastorage.mongo.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import cityguide.datastorage.core.db.GeoDbController;
import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;

@ExtendWith(MockitoExtension.class)
class ShowPlaceDaoMongoTest {

    @Mock
    private GeoDbController<ShowPlace> geoDbController;

    @Test
    void insertUpdateShowplaceShouldBeInsertNewShowPlace() {
        Mockito.when(geoDbController.getData(any())).thenReturn(Collections.emptyList());
        new ShowPlaceDaoMongo(geoDbController).insertUpdateShowplace(new ShowPlace());
        Mockito.verify(geoDbController).insertData(any());
    }

    @Test
    void insertUpdateShowplaceShouldBeUpdateExistingShowPlace() {
        Mockito.when(geoDbController.getData(any())).thenReturn(Collections.singletonList(new ShowPlace()));
        new ShowPlaceDaoMongo(geoDbController).insertUpdateShowplace(new ShowPlace());
        Mockito.verify(geoDbController).updateData(any(), any());
    }

    @Test
    void getShowPlaceWhenMoreThanOneWithSameAddress() {
        Mockito.when(geoDbController.getData(any())).thenReturn(Arrays.asList(new ShowPlace(), new ShowPlace()));
        assertThatCode(() -> new ShowPlaceDaoMongo(geoDbController).getShowPlace("address"))
                .isInstanceOf(DaoMongoException.class);
    }

    @Test
    void getShowPlaceWhenNotFound() {
        Mockito.when(geoDbController.getData(any())).thenReturn(Collections.emptyList());
        assertThat(new ShowPlaceDaoMongo(geoDbController).getShowPlace("address")).isEmpty();
    }

    @Test
    void getShowPlace() {
        Mockito.when(geoDbController.getData(any())).thenReturn(Collections.singletonList(new ShowPlace()));
        assertThat(new ShowPlaceDaoMongo(geoDbController).getShowPlace("address")).isPresent();
    }

    @Test
    void getNearest() {
        new ShowPlaceDaoMongo(geoDbController).getNearest(new Location(), 100);
        Mockito.verify(geoDbController).getNearest(new Location(), 100);
    }

    @Test
    void getAllShowPlace() {
        new ShowPlaceDaoMongo(geoDbController).getAllShowPlace();
        Mockito.verify(geoDbController).getAllData();
    }

    @Test
    void getAllShowPlaceWithCoordinate() {
        new ShowPlaceDaoMongo(geoDbController).getAllShowPlace(true);
        Mockito.verify(geoDbController).getData(any());
    }
}
