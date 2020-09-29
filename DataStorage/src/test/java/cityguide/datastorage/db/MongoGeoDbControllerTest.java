package cityguide.datastorage.db;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;

public class MongoGeoDbControllerTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";
    private final static String DB_NAME = "cityguide-db-test";
    private final static String DB_COLLECTION = "cityguide-test";

    private final static MongoGeoDbController<ShowPlace> geoController = new MongoGeoDbController<ShowPlace>();

    @BeforeEach
    public void setUp() {
        geoController.loadData(DB_NAME, DB_COLLECTION, ShowPlace.class);
    }

    @AfterEach
    public void tearDown() {
        geoController.clearAllData();
    }

    @BeforeAll
    public static void beforeAll(){
        geoController.open(MONGO_URL);
    }

    @AfterAll
    public static void afterAll() {
        geoController.close();
    }

    @Test
    public void getNearestTest() {
        final var location = new Location().setLongitude(0.0).setLatitude(0.0);

        final var showPlace = new ShowPlace().setLocation(location);
        geoController.insertData(showPlace);

        final List<ShowPlace> showPlaceList = geoController.getNearest(location, 100);
        assertThat(showPlaceList.size()).isEqualTo(1);

        location.setLatitude(30.0).setLongitude(30.0);
        final List<ShowPlace> showPlaceList1 = geoController.getNearest(location, 100);
        assertThat(showPlaceList1.size()).isEqualTo(0);
    }
}
