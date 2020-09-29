package cityguide.datastorage.db;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

public interface DbController<T> {
    void insertData(T data);

    void updateData(T data, Document filter);

    List<T> getData(Document filter);

    List<T> getData(Bson filter);

    List<T> getAllData();

    void closeDb();
}
