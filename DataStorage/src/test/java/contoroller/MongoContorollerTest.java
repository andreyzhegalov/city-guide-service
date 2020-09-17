package contoroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import model.Address;
import model.GeoPosition;
import model.ShowPlace;

public class MongoContorollerTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";
    private final static MongoController mongoContoroller = new MongoController(MONGO_URL);

    @AfterEach
    public void tearDown() {
        mongoContoroller.clearAllData();
    }

    @AfterAll
    public static void afterAll(){
        mongoContoroller.closeClient();
    }

    @Test
    public void testClearData() {
        assertThatCode(() -> mongoContoroller.clearAllData()).doesNotThrowAnyException();
    }

    @Test
    public void testGetAllShowPlace() {
        mongoContoroller.clearAllData();
        assertThat(mongoContoroller.getAllData().size()).isZero();
    }

    @Test
    public void testInsertData() {
        final var address = new Address().setStreet("new street1").setHouse("1").setBlock("1");
        final var showPlace = new ShowPlace().setAdress(address).setAddressString(address.toString())
                .setLocation(new GeoPosition().setCoordinates(Arrays.asList(0.0, 0.0)));

        assertThat(mongoContoroller.getAllData().size()).isZero();
        assertThatCode(() -> mongoContoroller.insertUpdateData(showPlace)).doesNotThrowAnyException();
        assertThat(mongoContoroller.getAllData().size()).isEqualTo(1);
    }

    @Test
    public void testGetDataByAddress() {
        final var address = new Address().setStreet("new street1").setHouse("1").setBlock("1");
        final var showPlace = new ShowPlace().setAdress(address).setAddressString(address.toString())
                .setLocation(new GeoPosition().setCoordinates(Arrays.asList(0.0, 0.0)));
        mongoContoroller.insertUpdateData(showPlace);

        final Optional<ShowPlace> mayBeShowPlace = mongoContoroller.getData(address);
        assertThat(mayBeShowPlace).isNotEmpty();
        assertThat(mayBeShowPlace.get().getLocation().getCoordinates()).isEqualTo(Arrays.asList(0.0, 0.0));
    }

    @Test
    public void testUpdateShowPlace() {
        final var address = new Address().setStreet("new street1").setHouse("1").setBlock("1");
        final var showPlace = new ShowPlace().setAdress(address).setAddressString(address.toString())
                .setLocation(new GeoPosition().setCoordinates(Arrays.asList(0.0, 0.0)));
        mongoContoroller.insertUpdateData(showPlace);
        assertThat(mongoContoroller.getAllData().size()).isEqualTo(1);

        showPlace.setLocation(new GeoPosition().setCoordinates(Arrays.asList(1.0, 1.0)));

        assertThatCode(() -> mongoContoroller.insertUpdateData(showPlace)).doesNotThrowAnyException();

        assertThat(mongoContoroller.getAllData().size()).isEqualTo(1);

        final var updatedData = mongoContoroller.getData(address);
        assertThat(updatedData).isPresent();
        assertThat(updatedData.get().getLocation().getCoordinates()).isEqualTo(Arrays.asList(1.0, 1.0));
    }

    @Test
    public void testNearestShowPlace() {
        final var address = new Address().setStreet("new street1").setHouse("1").setBlock("1");
        final var showPlace = new ShowPlace().setAdress(address).setAddressString(address.toString())
                .setLocation(new GeoPosition().setCoordinates(Arrays.asList(0.0, 0.0)));
        mongoContoroller.insertUpdateData(showPlace);

        final List<ShowPlace> showPlaceList = mongoContoroller
                .getNearest(new GeoPosition().setCoordinates(Arrays.asList(0.0, 0.0)), 100);
        assertThat(showPlaceList.size()).isEqualTo(1);

        final List<ShowPlace> showPlaceList1 = mongoContoroller
                .getNearest(new GeoPosition().setCoordinates(Arrays.asList(30.0, 30.0)), 100);
        assertThat(showPlaceList1.size()).isEqualTo(0);
    }
}
