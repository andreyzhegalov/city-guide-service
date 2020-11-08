package cityguide.telegrambot.service;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import cityguide.telegrambot.controller.DataStorageRestController;
import cityguide.telegrambot.telegram.bot.TelegramBot;

@ExtendWith(MockitoExtension.class)
class TelegramBotServiceTest {

    @Mock
    private DataStorageRestController restController;

    @Mock
    private TelegramBot telegramBot;

    @Test
    void onMessageWithLocation() {
        final var telegramBotService = new TelegramBotService(telegramBot, restController);
        final var message = Mockito.mock(Message.class);
        final var location = Mockito.mock(Location.class);

        final float LATITUDE = 10.0F;
        final float LONGITUDE = 20.0F;
        Mockito.when(message.hasLocation()).thenReturn(true);
        Mockito.when(message.getLocation()).thenReturn(location);
        Mockito.when(location.getLatitude()).thenReturn(LATITUDE);
        Mockito.when(location.getLongitude()).thenReturn(LONGITUDE);

        telegramBotService.onMessage(message);

        Mockito.verify(restController).getShowPlaceDescription(eq(LATITUDE), eq(LONGITUDE), anyInt());
    }

    @Test
    void onMessageWithoutLocation() {
        final var telegramBotService = new TelegramBotService(telegramBot, restController);
        final var message = Mockito.mock(Message.class);

        Mockito.when(message.hasLocation()).thenReturn(false);

        final var mayBeMessage = telegramBotService.onMessage(message);
        assertThat(mayBeMessage).isEmpty();
    }
}
