package cityguide.datacollector.source.walkspb;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WalkspbBuildingTest {

    @Test
    public void getBaseUrlTest() {
        assertThat(WalkspbBuilding.getBaseUrl()).isEqualTo("https://walkspb.ru/istoriya-peterburga/zd");
    }

    @Test
    public void getPageCntTest() {
        assertThat(WalkspbBuilding.getPageCnt()).isEqualTo(35);
    }

    @Test
    public void getItemOnPage(){
        assertThat(WalkspbBuilding.getItemOnPage()).isEqualTo(20);
    }
}
