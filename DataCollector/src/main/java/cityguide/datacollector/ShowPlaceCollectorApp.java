package cityguide.datacollector;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@EnableScheduling
@SpringBootApplication
public class ShowPlaceCollectorApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ShowPlaceCollectorApp.class).web(WebApplicationType.NONE).run(args);
    }
}
