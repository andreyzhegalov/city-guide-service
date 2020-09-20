package cityguide.datastorage.contoroller;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ReturnDocument;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import cityguide.datastorage.model.Address;
import cityguide.datastorage.model.GeoPosition;
import cityguide.datastorage.model.ShowPlace;

public class MongoController implements DbController {
    private static final String MONGO_DATABASE_NAME = "mongo-db-showplace";
    private static final String MONGO_COLLECTION_NAME = "showplace";
    private final MongoClient mongoClient;
    private final MongoCollection<ShowPlace> showPlaceCollection;
    private final MongoDatabase database;

    public MongoController(String mongoUrl) {
        final ConnectionString connectionString = new ConnectionString(mongoUrl);
        final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();

        this.mongoClient = MongoClients.create(clientSettings);
        this.database = mongoClient.getDatabase(MONGO_DATABASE_NAME);
        this.showPlaceCollection = database.getCollection(MONGO_COLLECTION_NAME, ShowPlace.class);
        this.showPlaceCollection.createIndex(Indexes.geo2dsphere("location"));
    }

    public void closeClient() {
        mongoClient.close();
    }

    public void clearAllData() {
        showPlaceCollection.drop();
    }

    @Override
    public void insertUpdateData(ShowPlace showPlace) {
        final var findedShowPlace = getData(showPlace.getAdress());
        if (findedShowPlace.isEmpty()) {
            showPlaceCollection.insertOne(showPlace);
        } else {
            final Document filtredByAddress = new Document("address_string", showPlace.getAddressString());
            final FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions()
                    .returnDocument(ReturnDocument.AFTER);
            final ShowPlace updatedShowPlace = showPlaceCollection.findOneAndReplace(filtredByAddress, showPlace,
                    returnDocAfterReplace);
            if (updatedShowPlace == null) {
                throw new MongoContorollerException("Error updated showplace " + showPlace);
            }
        }
    }

    @Override
    public Optional<ShowPlace> getData(Address address) {
        final var findedShowPlace = showPlaceCollection.find(eq("address_string", address.toString()));
        final var listShowPlace = toList(findedShowPlace);
        if (listShowPlace.size() > 1) {
            throw new MongoContorollerException(
                    "Internal error. More than one showplace with address " + address.toString());
        }
        return (listShowPlace.isEmpty()) ? Optional.empty() : Optional.of(listShowPlace.get(0));
    }

    @Override
    public List<ShowPlace> getNearest(GeoPosition geoPosition, double radiusInMeter) {
        final double EARTH_RADIUS = 6371_000.0;
        double distanceInRad = radiusInMeter / EARTH_RADIUS;
        FindIterable<ShowPlace> result = showPlaceCollection.find(Filters.geoWithinCenterSphere("location",
                geoPosition.getLatitude(), geoPosition.getLongitude(), distanceInRad));
        return toList(result);
    }

    @Override
    public List<ShowPlace> getAllData() {
        return toList(showPlaceCollection.find());
    }

    private <T> List<T> toList(FindIterable<T> iterator) {
        List<T> list = new ArrayList<>();
        for (var place : iterator) {
            list.add(place);
        }
        return list;
    }
}
