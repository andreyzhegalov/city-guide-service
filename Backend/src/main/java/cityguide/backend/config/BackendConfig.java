package cityguide.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cityguide.datacollector.DataCollector;
import cityguide.datastorage.DataStorage;
import cityguide.datastorage.DataStorageImpl;
import cityguide.datastorage.contoroller.DbController;
import cityguide.datastorage.contoroller.MongoController;
import cityguide.geocoder.GeoCoder;
import cityguide.telegram.Telegram;
import cityguide.telegram.bot.CityGuideBot;


@Configuration
public class BackendConfig {
    @Bean
    public DataCollector dataCollector(){
        return new DataCollector();
    }

    @Bean
    public DbController dbController(){
        return new MongoController("mongodb://172.17.0.3");
    }

    @Bean
    public GeoCoder geoCoder(){
        // return new GeoCoder("token");
    }

    @Bean(destroyMethod = "closeDb")
    public DataStorage dataStorage(DbController dbController){
        return new DataStorageImpl(dbController);
    }

    @Bean
    public CityGuideBot cityGuideBot(){
        Telegram.initContext();
        // return new CityGuideBot("token");
    }
}

