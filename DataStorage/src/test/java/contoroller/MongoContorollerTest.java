package contoroller;

import org.junit.jupiter.api.Test;

import model.Adress;
import model.ShowPlace;

public class MongoContorollerTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";

    @Test
    public void setDataTest() {
        final MongoContoroller mongoContoroller = new MongoContoroller(MONGO_URL);
        final var showPlace = new ShowPlace();
        showPlace.setAdress(new Adress("12", "street", ""));

        mongoContoroller.setData(showPlace);
        mongoContoroller.closeClient();
    }
}
