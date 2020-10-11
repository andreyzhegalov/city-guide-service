package cityguide.datacollector.scheduling;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cityguide.datacollector.service.ShowPlaceCollectorServiceImpl;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    ShowPlaceCollectorServiceImpl dataCollectorService;

    // @Scheduled(cron ="0 30 1 ? * FRI")
    @PostConstruct
    public void onTimer() {
        logger.info("scheduled task running");
        dataCollectorService.start();
    }
}
