package templates;

import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public interface MongoTemplate {
    <T> ObjectId insert(T value);

    <T> List<T> find(Bson query, Class<T> tClass);
}
