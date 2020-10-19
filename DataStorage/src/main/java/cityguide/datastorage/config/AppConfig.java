package cityguide.datastorage.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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

    @Autowired private Environment env;

    @Bean
    MongoGeoDbController<ShowPlace> getMongoGeoDbController(MongoClient mongoClient){
        final var geoController = new MongoGeoDbController<ShowPlace>(mongoClient);
        // geoController.open(env.getProperty("mongo.url"));
        geoController.loadData(env.getProperty("mongo.db"), env.getProperty("mongo.collection"), ShowPlace.class);
        return geoController;
    }

    @Bean
    MongoClient getMongoClient(){
        final ConnectionString connectionString = new ConnectionString(env.getProperty("mongo.url"));
        final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();
        return MongoClients.create(clientSettings);
    }

    @PreDestroy
    private void closeMongoClient(MongoClient mongoClient) {
        mongoClient.close();
    }
}

