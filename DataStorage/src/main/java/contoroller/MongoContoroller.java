package contoroller;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.bson.Document;

import model.Adress;
import model.GeoPosition;
import model.ShowPlace;
import templates.MongoTemplate;
import templates.MongoTemplateImpl;

public class MongoContoroller implements DbController {
    private static final String MONGO_DATABASE_NAME = "mongo-db-showplace";
    private static final String PHONES_COLLECTION_NAME = "showplace";
    private final MongoTemplate mongoTemplate;
    private final MongoClient mongoClient;

    public MongoContoroller(String mongoUrl) {
        this.mongoClient = MongoClients.create(mongoUrl);
        final var mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final var database = mongoClient.getDatabase(MONGO_DATABASE_NAME);
        final var collection = database.getCollection(PHONES_COLLECTION_NAME);
        this.mongoTemplate = new MongoTemplateImpl(collection, mapper);
    }

    public void closeClient() {
        mongoClient.close();
    }

    @Override
    public void setData(ShowPlace showPlace) {
        final var id = mongoTemplate.insert(showPlace);
        if (id == null) {
            throw new MongoContorollerException("Insert showplace failed");
        }
    }

    @Override
    public List<ShowPlace> getData(Adress adress) {
        final String jsonAdress;
        try {
            jsonAdress = adress.toJson();
        } catch (JsonProcessingException e) {
            throw new MongoContorollerException(e.getMessage());
        }
        final String queryJson = "{\"adress\":" + jsonAdress + "}";
        final var resList = mongoTemplate.find(Document.parse(queryJson), ShowPlace.class);
        return resList;
    }

    @Override
    public List<ShowPlace> getNearest(GeoPosition geoPosition) {
        // TODO Auto-generated method stub
        return null;
    }

}
