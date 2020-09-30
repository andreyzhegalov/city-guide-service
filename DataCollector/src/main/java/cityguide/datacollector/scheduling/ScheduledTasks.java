package cityguide.datacollector.scheduling;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cityguide.datacollector.service.DataCollectorServiceImpl;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    DataCollectorServiceImpl dataCollectorService;

    // @Scheduled(fixedDelay = 5_000)
    @PostConstruct
    public void onTimer() {
        logger.info("scheduled task running");
        dataCollectorService.start();
    }
}
