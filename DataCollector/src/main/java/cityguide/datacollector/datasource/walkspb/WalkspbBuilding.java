package cityguide.datacollector.datasource.walkspb;

// TODO replace to config
public class WalkspbBuilding {
    private final static String BASE_URL = "https://walkspb.ru/istoriya-peterburga/zd";
    private final static int PAGE_CNT = 35;
    private final static int ITEM_ON_PAGE = 20;

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static int getPageCnt() {
        return PAGE_CNT;
    }

    public static int getItemOnPage() {
        return ITEM_ON_PAGE;
    }
}
