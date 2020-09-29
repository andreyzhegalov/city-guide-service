package cityguide.datastorage;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cityguide.datastorage.model.Address;
import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.Location;
import cityguide.datastorage.model.ShowPlace;

// https://mongodb.github.io/mongo-java-driver/3.5/driver/getting-started/quick-start-pojo/
// https://www.mongodb.com/blog/post/quick-start-java-and-mongodb--mapping-pojos?utm_campaign=javapojos&utm_source=twitter&utm_medium=organic_social
// https://www.baeldung.com/mongodb-geospatial-support
// https://docs.mongodb.com/manual/geospatial-queries/
class CommonTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";
    private static final String MONGO_DATABASE_NAME = "mongo-db-test";
    private static final String MONGO_COLLECTION_NAME = "cityGuideTest";
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<ShowPlace> showPlaceCollection;

    @BeforeAll
    static void setUp() {
        final ConnectionString connectionString = new ConnectionString(MONGO_URL);
        final CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        final CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .codecRegistry(codecRegistry).build();

        mongoClient = MongoClients.create(clientSettings);
        database = mongoClient.getDatabase(MONGO_DATABASE_NAME);
        showPlaceCollection = database.getCollection(MONGO_COLLECTION_NAME, ShowPlace.class);
    }

    @AfterAll
    static void tearDown() {
        showPlaceCollection.drop();
        mongoClient.close();
    }


    @Test
    public void commonTest() {
        final var address1 = new Address().setStreet("new street1").setHouse("1").setBlock("1");
        final var showPlace1 = new ShowPlace()
            .setAdress(address1)
            .setAddressString(address1.toString())
            .setLocation(new Location().setCoordinates(Arrays.asList(0.0, 0.0)));

        final var address2 = new Address().setStreet("new street2").setHouse("2").setBlock("2");
        final var showPlace2 = new ShowPlace()
            .setAdress(address2)
            .setAddressString(address2.toString())
            .setLocation(new Location().setCoordinates(Arrays.asList(45.0, 45.0)));

        showPlaceCollection.insertOne(showPlace1);
        showPlaceCollection.insertOne(showPlace2);

        System.out.println("All init documents");
        for (var place : showPlaceCollection.find()) {
            System.out.println("-----" + place);
        }

        // find by adress
        final var findedShowPlace = showPlaceCollection.find(eq("address_string", address1.toString()));
        System.out.println("Filtred documents");
        for (var place : findedShowPlace) {
            System.out.println("-----" + place);
        }

        // add description
        final var showPlace = findedShowPlace.first();
        final var descriptionList = showPlace.getDescriptionList();
        final var newDescription = new Description();
        newDescription.setTitle("title1");
        descriptionList.add(newDescription);
        showPlace.setDescriptionList(descriptionList);

        final Document filtredByAddress = new Document("address_string", showPlace.getAddressString());
        final FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions()
                .returnDocument(ReturnDocument.AFTER);

        final ShowPlace updatedShowPlace = showPlaceCollection.findOneAndReplace(filtredByAddress, showPlace, returnDocAfterReplace);
        assertEquals(1, updatedShowPlace.getDescriptionList().size());

        System.out.println("All documents after updated");
        for (var place : showPlaceCollection.find()) {
            System.out.println("-----" + place);
        }

        // find near by position
        double distanceInRad = 15.0 / 6371;
        FindIterable<ShowPlace> result = showPlaceCollection.find(Filters.geoWithinCenterSphere("location", 0.0, 0.0, distanceInRad));
        System.out.println("Finded by coord");
        for (var place : result) {
            System.out.println("-----" + place);
        }
    }
}
