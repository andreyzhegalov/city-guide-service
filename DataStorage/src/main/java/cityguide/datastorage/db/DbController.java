package cityguide.datastorage.db;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

public interface DbController<T> {
    public void insertData(T data);

    public void updateData(T data, Document filter);

    public List<T> getData(Document filter);

    public List<T> getData(Bson filter);

    public List<T> getAllData();

    void closeDb();
}
