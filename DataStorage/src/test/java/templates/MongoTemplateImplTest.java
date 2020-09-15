package templates;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestObj {
    private ObjectId _id;
    private String field1;

    public TestObj() {
    }

    public TestObj(String field1) {
        this.field1 = field1;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }
}

class MongoTemplateImplTest {
    private final static String MONGO_URL = "mongodb://172.17.0.3";
    private static final String MONGO_DATABASE_NAME = "mongo-db-test";
    private static final String PHONES_COLLECTION_NAME = "cityGuideTest";
    private static MongoTemplate mongoTemplate;
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;

    @BeforeAll
    static void setUp() {
        mongoClient = MongoClients.create(MONGO_URL);
        final var mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        database = mongoClient.getDatabase(MONGO_DATABASE_NAME);
        collection = database.getCollection(PHONES_COLLECTION_NAME);
        mongoTemplate = new MongoTemplateImpl(collection, mapper);
    }

    @AfterAll
    static void tearDown() {
        collection.drop();
        mongoClient.close();
    }

    @Test
    public void testInsert() {
        final var id = mongoTemplate.insert(new TestObj("testValue"));
        assertNotNull(id);
    }

    @Test
    public void findIfNotExist() {
        final var resList = mongoTemplate.find(eq("field1", "testValue"), TestObj.class);
        assertEquals(0, resList.size());
    }

    @Test
    public void findIfExist() {
        final String TEST_VALUE = "testValue";
        mongoTemplate.insert(new TestObj(TEST_VALUE));
        final var resList = mongoTemplate.find(eq("field1",TEST_VALUE ), TestObj.class);
        assertEquals(1, resList.size());
        assertEquals(TEST_VALUE, resList.get(0).getField1());
    }
}
