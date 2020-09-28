package cityguide.datacollector.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cityguide.datacollector.service.DataCollectorService;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    DataCollectorService dataCollectorService;

    // @Scheduled(fixedRate = 15000)
    @Scheduled(fixedDelay = 5_000)
    public void onTimer() {
        logger.info("sheduled task running");
        dataCollectorService.start();
    }
}
