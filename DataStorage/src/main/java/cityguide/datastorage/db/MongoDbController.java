package cityguide.datastorage.db;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDbController<T> implements DbController<T> {
    private final static Logger logger = LoggerFactory.getLogger(MongoDbController.class);
    private MongoClient mongoClient;
    private MongoCollection<T> dataCollection;

    public void loadData(String dbName, String collectionName, Class<T> dataClass) {
        checkConnection();
        final MongoDatabase database = mongoClient.getDatabase(dbName);
        this.dataCollection = database.getCollection(collectionName, dataClass);
    }

    public void clearAllData() {
        dataCollection.drop();
    }

    public MongoCollection<T> getDataCollection() {
        return dataCollection;
    }

    @Override
    public void close() {
        checkConnection();
        mongoClient.close();
    }

    @Override
    public void insertData(T data) {
        dataCollection.insertOne(data);
    }

    @Override
    public void updateData(T data, Document filter) {
        final FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions()
                .returnDocument(ReturnDocument.AFTER);
        dataCollection.findOneAndReplace(filter, data, returnDocAfterReplace);
    }

    @Override
    public List<T> getData(Document filter) {
        return toList(dataCollection.find(filter));
    }

    @Override
    public List<T> getData(Bson filter) {
        return toList(dataCollection.find(filter));
    }

    @Override
    public List<T> getAllData() {
        return toList(dataCollection.find());
    }

    List<T> toList(FindIterable<T> iterator) {
        List<T> list = new ArrayList<>();
        for (var place : iterator) {
            list.add(place);
        }
        return list;
    }

    private void checkConnection(){
        if( this.mongoClient == null){
            throw new MongolControllerException("No connection with mongoDb");
        }
    }

    @Override
    public void open(String url) {
        logger.info("Connect to MongoDb by address {}", url);

        final ConnectionString connectionString = new ConnectionString(url);
        final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();
        this.mongoClient = MongoClients.create(clientSettings);
    }
}
