package cityguide.datastorage.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cityguide.datastorage.model.Description;
import cityguide.datastorage.model.ShowPlace;

class TelegramMessageViewImplTest {

    @Test
    void prepareMessageForNotFoundShowPlace() {
        assertThat(new TelegramMessageViewImpl().prepareMessage(Collections.emptyList()))
                .isEqualTo("Ничего не найдено");
    }

    private ShowPlace prepareShowPlace() {
        final var showPlace = new ShowPlace();
        final var descriptionList = new ArrayList<Description>();

        final var description = new Description();
        description.setInfo("some description");
        descriptionList.add(description);

        final var description2 = new Description();
        description2.setInfo("some description2");
        descriptionList.add(description2);

        showPlace.setDescriptionList(descriptionList);
        showPlace.setAddressString("house1");
        return showPlace;
    }

    @Test
    void messageShouldContainAddress() {
        final var showPlaceList = Collections.singletonList(prepareShowPlace());
        final var messageLines = Arrays.asList("house1", "some description", "house1", "some description2");
        final var resultMessage = messageLines.stream().collect(Collectors.joining(System.lineSeparator()));
        assertThat(new TelegramMessageViewImpl().prepareMessage(showPlaceList)).isEqualTo(resultMessage);
    }
}
