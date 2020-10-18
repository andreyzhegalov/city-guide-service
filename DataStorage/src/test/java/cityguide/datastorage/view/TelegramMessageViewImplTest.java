package cityguide.datastorage.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.ShowPlace;

class TelegramMessageViewImplTest {

    @Test
    void prepareMessageForNotFoundShowPlace() {
        assertThat(new TelegramMessageViewImpl().prepareMessage(Collections.emptyList())).isEqualTo("Ничего не найдено");
    }

    private ShowPlace prepareShowPlace(){
        final var showPlace = new ShowPlace();
        final var descriptionList = new ArrayList<Description>();
        final var description = new Description();
        description.setInfo("some description");
        descriptionList.add(description);
        showPlace.setDescriptionList(descriptionList);
        return showPlace;
    }

    @Test
    void prepareMessage(){
        final var showPlaceList = Collections.singletonList(prepareShowPlace());
        assertThat(new TelegramMessageViewImpl().prepareMessage(showPlaceList)).isEqualTo("some description");
    }
}
