package templates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class MongoTemplateImpl implements MongoTemplate {
    private static final TypeReference<Map<String, Object>> STR_OBJECT_MAP_TYPE_REF = new TypeReference<>() {};
    private final MongoCollection<Document> collection;
    private final ObjectMapper mapper;

    public MongoTemplateImpl( MongoCollection<Document> collection, ObjectMapper mapper) {
        this.collection = collection;
        this.mapper = mapper;
    }

    @Override
    public <T> ObjectId insert(T value) {
        final var document = new Document(mapper.convertValue(value, STR_OBJECT_MAP_TYPE_REF));
        document.remove("_id");
        collection.insertOne(document);
        return (ObjectId) document.get("_id");
    }

    @Override
    public <T> List<T> find(Bson query, Class<T> tClass){
        final var documents = collection.find(query);

        final var res = new ArrayList<T>();
        final var cursor = documents.cursor();
        while (cursor.hasNext()) {
            final var document = cursor.next();
            res.add(document2Object(document, tClass));
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    private <T> T document2Object(Document document, Class<T> tClass) {
        if (tClass.equals(Document.class)) {
            return (T)document;
        }
        final var id = document.get("_id");
        document.put("_id", id.toString());
        try {
            return mapper.reader().forType(tClass).readValue(document.toJson());
        } catch (JsonProcessingException e) {
            throw new MongoTemplateException(e.getMessage());
        }
    }
}
