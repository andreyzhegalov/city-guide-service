package cityguide.datastorage.core.db;

import java.util.List;

import org.bson.conversions.Bson;

public interface DbController<T> {
    void insertData(T data);

    void updateData(T data, Bson filter);

    List<T> getData(Bson filter);

    List<T> getAllData();
}
