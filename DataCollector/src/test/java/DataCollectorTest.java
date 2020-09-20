import java.util.function.Consumer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCollectorTest {
    private static Logger logger = LoggerFactory.getLogger(DataCollectorTest.class);

    @Test
    @Disabled
    public void startTest() {
        final var dataCollector = new DataCollector();
        dataCollector.start();
    }

    class CallbackTest implements Consumer<DataType> {
        @Override
        public void accept(DataType t) {
            logger.info("callback invoke with arg ");
        }
    }

    @Test
    @Disabled
    public void callbackInvokeTest() {
        final var dataCollector = new DataCollector();
        final var callback = new CallbackTest();
        dataCollector.setCallback(callback);
        dataCollector.start();
    }
}
