package cityguide.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import cityguide.datacollector.DataCollector;
import cityguide.datastorage.DataStorage;
import cityguide.datastorage.DataStorageImpl;
import cityguide.datastorage.contoroller.MongoGeoDbController;
import cityguide.datastorage.contoroller.GeoDbController;
import cityguide.datastorage.model.ShowPlace;
import cityguide.geocoder.GeoCoder;
import cityguide.telegram.Telegram;
import cityguide.telegram.bot.TelegramBot;

@Configuration
@PropertySource("classpath:backend.properties")
public class BackendConfig {
    @Value("${mongo.url}")
    private String mongoUrl;

    @Value("${mongo.db.name}")
    private String mongoDbName;

    @Value("${mongo.collection.name}")
    private String mongoCollectionName;

    @Value("${geocoder.token}")
    private String geoCoderToken;

    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Value("${telegram.bot.name}")
    private String telegramBotName;

    @Bean
    public DataCollector dataCollector() {
        return new DataCollector();
    }

    @Bean
    public GeoDbController<ShowPlace> dbController() {
        final var geoController = new MongoGeoDbController<ShowPlace>(mongoUrl);
        geoController.loadData(mongoDbName, mongoCollectionName, ShowPlace.class);
        return geoController;
    }

    @Bean
    public GeoCoder geoCoder() {
        return new GeoCoder(geoCoderToken);
    }

    @Bean(destroyMethod = "closeDb")
    public DataStorage dataStorage(GeoDbController<ShowPlace> dbController) {
        return new DataStorageImpl(dbController);
    }

    @Bean
    public TelegramBot cityGuideBot() {
        Telegram.initContext();
        return new TelegramBot(telegramBotName, telegramBotToken);
    }
}
