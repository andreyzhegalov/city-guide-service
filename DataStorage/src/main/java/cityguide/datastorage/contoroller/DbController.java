package cityguide.datastorage.contoroller;

import java.util.List;

import org.bson.Document;

public interface DbController<T> {
    public void insertData(T data);

    public void updateData(T data, Document filter);

    public List<T> getData(Document filter);

    public List<T> getAllData();

    void closeDb();
}
