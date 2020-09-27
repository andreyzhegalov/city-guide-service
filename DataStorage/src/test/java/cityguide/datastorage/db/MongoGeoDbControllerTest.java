package cityguide.datastorage.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;

public class MongoGeoDbControllerTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";
    private final static String DB_NAME = "cityguide-db-test";
    private final static String DB_COLLECTION = "cityguide-test";

    private final static MongoGeoDbController<ShowPlace> geoController = new MongoGeoDbController<ShowPlace>(MONGO_URL);

    @BeforeEach
    public void setUp() {
        geoController.loadData(DB_NAME, DB_COLLECTION, ShowPlace.class);
    }

    @AfterEach
    public void tearDown() {
        geoController.clearAllData();
    }

    @AfterAll
    public static void afterAll() {
        geoController.closeDb();
    }

    @Test
    public void getNearestTest() {
        final var position = new GeoPosition().setLongitude(0.0).setLatitude(0.0);

        final var showPlace = new ShowPlace().setLocation(position);
        geoController.insertData(showPlace);

        final List<ShowPlace> showPlaceList = geoController.getNearest(position, 100);
        assertThat(showPlaceList.size()).isEqualTo(1);

        position.setLatitude(30.0).setLongitude(30.0);
        final List<ShowPlace> showPlaceList1 = geoController.getNearest(position, 100);
        assertThat(showPlaceList1.size()).isEqualTo(0);
    }
}
