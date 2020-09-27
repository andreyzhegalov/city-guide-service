package cityguide.datastorage.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.bson.Document;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cityguide.datastorage.model.Address;
import cityguide.datastorage.model.ShowPlace;

public class MongoDbControllerTest {

    private final static String MONGO_URL = "mongodb://172.17.0.3";
    private final static String DB_NAME = "cityguide-db-test";
    private final static String DB_COLLECTION = "cityguide-test";

    private final static MongoDbController<ShowPlace> mongoContoroller = new MongoDbController<ShowPlace>(MONGO_URL);

    @BeforeEach
    public void setUp(){
        mongoContoroller.loadData(DB_NAME, DB_COLLECTION, ShowPlace.class);
    }

    @AfterEach
    public void tearDown() {
        mongoContoroller.clearAllData();
    }

    @AfterAll
    public static void afterAll(){
        mongoContoroller.closeDb();
    }

    @Test
    public void testInsertData() {
        final var showPlace = new ShowPlace().setAddressString("address1");
        assertThat(mongoContoroller.getAllData()).isEmpty();
        assertThatCode(() -> mongoContoroller.insertData(showPlace)).doesNotThrowAnyException();
        assertThat(mongoContoroller.getAllData()).hasSize(1);
    }

    @Test
    public void testUpdateData(){
        final var showPlace = new ShowPlace().setAddressString("address1");
        mongoContoroller.insertData(showPlace);
        assertThat(mongoContoroller.getAllData()).hasSize(1);
        assertThat(mongoContoroller.getAllData().get(0).getAdress()).isNull();

        final var address = new Address().setStreet("new street1");
        showPlace.setAdress(address);

        final var filter = new Document("address_string", showPlace.getAddressString());
        assertThatCode(() -> mongoContoroller.updateData(showPlace, filter)).doesNotThrowAnyException();
        assertThat(mongoContoroller.getAllData()).hasSize(1);

        final var updatedData = mongoContoroller.getAllData();
        assertThat(updatedData).hasSize(1);
        assertThat(updatedData.get(0).getAdress()).isNotNull();
        assertThat(updatedData.get(0).getAdress().getStreet()).isEqualTo("new street1");
    }

    @Test
    public void getDataTest(){
        final var showPlace = new ShowPlace().setAddressString("address1");
        final var filter = new Document("address_string", showPlace.getAddressString());

        assertThat(mongoContoroller.getData(filter)).hasSize(0);
        mongoContoroller.insertData(showPlace);
        assertThat(mongoContoroller.getData(filter)).hasSize(1);
    }

    @Test
    public void testClearData() {
        assertThatCode(() -> mongoContoroller.clearAllData()).doesNotThrowAnyException();
    }

    @Test
    public void testGetAllData() {
        final var showPlace1 = new ShowPlace().setAddressString("address1");
        final var showPlace2 = new ShowPlace().setAddressString("address2");

        assertThat(mongoContoroller.getAllData()).isEmpty();
        mongoContoroller.insertData(showPlace1);
        assertThat(mongoContoroller.getAllData()).hasSize(1);
        mongoContoroller.insertData(showPlace2);
        assertThat(mongoContoroller.getAllData()).hasSize(2);
    }
}

