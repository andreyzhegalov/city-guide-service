package cityguide.datacollector.scheduling;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cityguide.datacollector.service.DataCollectorService;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    DataCollectorService dataCollectorService;

    // @Scheduled(fixedDelay = 5_000)
    @PostConstruct
    public void onTimer() {
        logger.info("sheduled task running");
        dataCollectorService.start();
    }
}
