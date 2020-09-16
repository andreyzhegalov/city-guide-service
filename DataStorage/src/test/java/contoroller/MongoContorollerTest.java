package contoroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.Address;
import model.ShowPlace;

public class MongoContorollerTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";

    @Test
    public void testSaveSelectByAdress() {
        final MongoContoroller mongoContoroller = new MongoContoroller(MONGO_URL);
        final var showPlace = new ShowPlace();
        final var adress = new Address();
        adress.setStreet("new street");
        adress.setHouse("12");
        adress.setBlock("2");
        showPlace.setAdress(adress);
        showPlace.setAddressString(adress.toString());

        mongoContoroller.setData(showPlace);

        final var showPlaceList = mongoContoroller.getData(adress);
        assertEquals(1, showPlaceList.size());

        mongoContoroller.closeClient();
    }

}
