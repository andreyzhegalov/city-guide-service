package cityguide.datacollector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("cityguide")
public class ContextConfig {
    @Bean
    public RestTemplate getRestController(){
        return new RestTemplate();
    }

}

