package cityguide.datastorage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import cityguide.datastorage.db.MongoGeoDbController;
import cityguide.datastorage.model.ShowPlace;

@Configuration
@ComponentScan("cityguide")
// @EnableConfigurationProperties
// @ConfigurationProperties
@PropertySource("classpath:application.yml")
public class AppConfig {

    @Autowired private Environment env;

    @Bean
    MongoGeoDbController<ShowPlace> getMongoGeoDbController(){
        final var geoController = new MongoGeoDbController<ShowPlace>(env.getProperty("mongo.url"));
        geoController.loadData(env.getProperty("mongo.db"), env.getProperty("mongo.collection"), ShowPlace.class);
        return geoController;
    }
}

