package source.walkspb;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;

public class ItemParserTest {
    private final static String testUrl = "https://walkspb.ru/istoriya-peterburga/zd/millionnaya22";

    @Test
    public void parseAdressTest() throws MalformedURLException{
        final var addressList =  new ItemParser(new URL(testUrl)).getAddresses();
        assertThat(addressList.size()).isEqualTo(3);
    }

    @Test
    public void getDescriptionTest() throws MalformedURLException{
        final var description = new ItemParser(new URL(testUrl)).getDescription();
        assertThat(description).isNotEmpty();
    }
}

