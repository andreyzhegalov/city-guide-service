package cityguide.datastorage.mongo.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cityguide.datastorage.model.Address;
import cityguide.datastorage.model.ShowPlace;

public class MongoDbControllerTest {

    private final static String DB_NAME = "cityguide-db-test";
    private final static String DB_COLLECTION = "cityguide-test";

    private static MongoClient mongoClient;
    private static MongoDbController<ShowPlace> mongoContoroller;

    private static MongoDbContainer mongoDbContainer;

    @BeforeAll
    static void beforeAll() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
        final var host = mongoDbContainer.getHost();
        final var port = mongoDbContainer.getPort();
        final ConnectionString connectionString = new ConnectionString("mongodb://" + host + ":" + port);

        final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);

        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();

        mongoClient = MongoClients.create(clientSettings);

        mongoContoroller = new MongoDbController<>(mongoClient);
    }

    @AfterAll
    static void afterAll() {
        mongoClient.close();
        mongoDbContainer.stop();
    }

    @BeforeEach
    void setUp() {
        mongoContoroller.loadData(DB_NAME, DB_COLLECTION, ShowPlace.class);
    }

    @AfterEach
    public void tearDown() {
        mongoContoroller.clearAllData();
    }

    @Test
    void checkConnectionToMongoClient() {
        assertThatCode(() -> new MongoDbController<ShowPlace>(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void testInsertData() {
        final var showPlace = new ShowPlace();
        showPlace.setAddressString("address1");
        assertThat(mongoContoroller.getAllData()).isEmpty();
        assertThatCode(() -> mongoContoroller.insertData(showPlace)).doesNotThrowAnyException();
        assertThat(mongoContoroller.getAllData()).hasSize(1);
    }

    @Test
    void testUpdateData() {
        final var showPlace = new ShowPlace();
        showPlace.setAddressString("address1");
        mongoContoroller.insertData(showPlace);
        assertThat(mongoContoroller.getAllData()).hasSize(1);
        assertThat(mongoContoroller.getAllData().get(0).getAddress()).isNull();

        final var address = new Address();
        address.setStreet("new street1");
        showPlace.setAddress(address);

        final var filter = new Document("address_string", showPlace.getAddressString());
        assertThatCode(() -> mongoContoroller.updateData(showPlace, filter)).doesNotThrowAnyException();
        assertThat(mongoContoroller.getAllData()).hasSize(1);

        final var updatedData = mongoContoroller.getAllData();
        assertThat(updatedData).hasSize(1);
        assertThat(updatedData.get(0).getAddress()).isNotNull();
        assertThat(updatedData.get(0).getAddress().getStreet()).isEqualTo("new street1");
    }

    @Test
    void getDataTest() {
        final var showPlace = new ShowPlace();
        showPlace.setAddressString("address1");
        final var filter = new Document("address_string", showPlace.getAddressString());

        assertThat(mongoContoroller.getData(filter)).hasSize(0);
        mongoContoroller.insertData(showPlace);
        assertThat(mongoContoroller.getData(filter)).hasSize(1);
    }

    @Test
    void testClearData() {
        assertThatCode(() -> mongoContoroller.clearAllData()).doesNotThrowAnyException();
    }

    @Test
    void testGetAllData() {
        final var showPlace1 = new ShowPlace();
        showPlace1.setAddressString("address1");
        final var showPlace2 = new ShowPlace();
        showPlace2.setAddressString("address2");

        assertThat(mongoContoroller.getAllData()).isEmpty();
        mongoContoroller.insertData(showPlace1);
        assertThat(mongoContoroller.getAllData()).hasSize(1);
        mongoContoroller.insertData(showPlace2);
        assertThat(mongoContoroller.getAllData()).hasSize(2);
    }
}
