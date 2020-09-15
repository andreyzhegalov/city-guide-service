package contoroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import model.Adress;
import model.ShowPlace;

public class MongoContorollerTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";

    @Test
    public void testSaveSelectByAdress() {
        final MongoContoroller mongoContoroller = new MongoContoroller(MONGO_URL);
        final var showPlace = new ShowPlace();
        final var adress = new Adress("12", "street", "");
        showPlace.setAdress(new Adress("12", "street", ""));

        mongoContoroller.setData(showPlace);

        final var showPlaceList = mongoContoroller.getData(adress);
        assertEquals(1, showPlaceList.size());

        mongoContoroller.closeClient();
    }

}
