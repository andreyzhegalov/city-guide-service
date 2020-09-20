package cityguide.datastorage;

import cityguide.datastorage.model.ShowPlace;

public interface DataStorage {
    void setData(ShowPlace showPlace);

    ShowPlace getData();
}
