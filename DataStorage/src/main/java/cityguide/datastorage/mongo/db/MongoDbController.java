package cityguide.datastorage.mongo.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import cityguide.datastorage.core.db.DbController;

@Repository
public class MongoDbController<T> implements DbController<T> {
    private final MongoClient mongoClient;
    private MongoCollection<T> dataCollection;

    public MongoDbController(MongoClient mongoClient){
        Objects.requireNonNull(mongoClient);
        this.mongoClient = mongoClient;
    }

    public void loadData(String dbName, String collectionName, Class<T> dataClass) {
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
    public void insertData(T data) {
        dataCollection.insertOne(data);
    }

    @Override
    public void updateData(T data, Bson filter) {
        final FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions()
                .returnDocument(ReturnDocument.AFTER);
        dataCollection.findOneAndReplace(filter, data, returnDocAfterReplace);
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
}
