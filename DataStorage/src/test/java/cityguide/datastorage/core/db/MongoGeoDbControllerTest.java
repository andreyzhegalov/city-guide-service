package cityguide.datastorage.core.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;
import cityguide.datastorage.mongo.db.MongoGeoDbController;

public class MongoGeoDbControllerTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";
    private final static String DB_NAME = "cityguide-db-test";
    private final static String DB_COLLECTION = "cityguide-test";

    private static MongoClient mongoClient;
    private static MongoGeoDbController<ShowPlace> geoController;

    @BeforeAll
    public static void beforeAll() {
        final ConnectionString connectionString = new ConnectionString(MONGO_URL);
        final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();
        mongoClient = MongoClients.create(clientSettings);
        geoController = new MongoGeoDbController<>(mongoClient);
    }

    @AfterAll
    public static void afterAll() {
        mongoClient.close();
    }

    @BeforeEach
    public void setUp() {
        geoController.loadData(DB_NAME, DB_COLLECTION, ShowPlace.class);
    }

    @AfterEach
    public void tearDown() {
        geoController.clearAllData();
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
