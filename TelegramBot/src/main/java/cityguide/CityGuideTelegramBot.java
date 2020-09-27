package cityguide;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CityGuideTelegramBot {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CityGuideTelegramBot.class).web(WebApplicationType.NONE).run(args);
    }
}
