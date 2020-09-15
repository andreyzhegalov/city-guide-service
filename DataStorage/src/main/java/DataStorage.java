import model.ShowPlace;

public interface DataStorage {
    void setData(ShowPlace showPlace);

    ShowPlace getData();
}
