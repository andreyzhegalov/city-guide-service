package cityguide.datacollector.datasource.walkspb;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

public class ItemParserTest {
    private final static String testUrl = "https://walkspb.ru/istoriya-peterburga/zd/millionnaya22";

    @Test
    public void parseAddressTest() throws MalformedURLException{
        // final var addressList =  new WalkSpbItemParser(new URL(testUrl)).getAddresses();
        // assertThat(addressList.size()).isEqualTo(3);
    }

    @Test
    public void getDescriptionTest() throws MalformedURLException{
        // final var description = new WalkSpbItemParser(new URL(testUrl)).getDescription();
        // assertThat(description).isNotEmpty();
    }
}

