package cityguide.datastorage.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.Objects;

import javax.annotation.PreDestroy;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import cityguide.datastorage.model.ShowPlace;
import cityguide.datastorage.mongo.db.MongoGeoDbController;

@Configuration
@ComponentScan("cityguide")
@PropertySource("classpath:application.yml")
public class AppConfig {

    @Autowired
    private Environment env;

    private MongoClient mongoClient;

    @Bean
    public MongoGeoDbController<ShowPlace> getMongoGeoDbController(MongoClient mongoClient) {
        this.mongoClient = Objects.requireNonNull(mongoClient);
        final var geoController = new MongoGeoDbController<ShowPlace>(mongoClient);
        geoController.loadData(Objects.requireNonNull(env.getProperty("mongo.db")),
                Objects.requireNonNull(env.getProperty("mongo.collection")), ShowPlace.class);
        return geoController;
    }

    @Bean
    public MongoClient getMongoClient() {
        final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        final ConnectionString connectionString = new ConnectionString(
                Objects.requireNonNull(env.getProperty("mongo.url")));
        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();
        return MongoClients.create(clientSettings);
    }

    @PreDestroy
    private void closeMongoClient() {
        mongoClient.close();
    }
}
