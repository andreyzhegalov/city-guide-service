package cityguide.telegrambot.telegram.bot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

class TelegramBotTest {

    @Test
    void whenNewTelegramUpdateNotContainsMessage() {
        final var telegrambot = new TelegramBot("name", "token");
        final var newUpdate = Mockito.mock(Update.class);
        final var messageHandler = Mockito.mock(MessageHandler.class);

        Mockito.when(newUpdate.hasMessage()).thenReturn(false);

        telegrambot.setMessageHandler(messageHandler);
        telegrambot.onUpdateReceived(newUpdate);

        Mockito.verify(messageHandler, never()).apply(any());
    }

    @Test
    void shouldNotSendResponceIfMessageHandlerNotPresent() {
        final var telegrambot = Mockito.spy(new TelegramBot("name", "token"));

        final var newUpdate = Mockito.mock(Update.class);
        final var message = Mockito.mock(Message.class);

        Mockito.when(newUpdate.hasMessage()).thenReturn(true);
        Mockito.when(newUpdate.getMessage()).thenReturn(message);

        telegrambot.onUpdateReceived(newUpdate);

        Mockito.verify(telegrambot, never()).sendMessage(any());
    }

    @Test
    void onNewTelegramMessageShouldInvokeCallback() {
        final var telegrambot = new TelegramBot("name", "token");
        final var messageHandler = Mockito.mock(MessageHandler.class);
        final var newUpdate = Mockito.mock(Update.class);
        final var message = Mockito.mock(Message.class);

        Mockito.when(newUpdate.hasMessage()).thenReturn(true);
        Mockito.when(newUpdate.getMessage()).thenReturn(message);
        Mockito.when(messageHandler.apply(message)).thenReturn(Optional.empty());

        telegrambot.setMessageHandler(messageHandler);
        telegrambot.onUpdateReceived(newUpdate);

        Mockito.verify(messageHandler).apply(eq(message));
    }

    @Test
    void onMessageHandlerReturnResponse() throws TelegramApiException {
        final var telegrambot = Mockito.spy(new TelegramBot("name", "token"));
        Mockito.doNothing().when(telegrambot).sendMessage(any());

        final var newUpdate = Mockito.mock(Update.class);
        final var message = Mockito.mock(Message.class);
        Mockito.when(newUpdate.hasMessage()).thenReturn(true);
        Mockito.when(newUpdate.getMessage()).thenReturn(message);

        final var messageHandler = Mockito.mock(MessageHandler.class);
        Mockito.when(messageHandler.apply(message)).thenReturn(Optional.of("response message"));

        telegrambot.setMessageHandler(messageHandler);
        telegrambot.onUpdateReceived(newUpdate);

        Mockito.verify(telegrambot).sendMessage(any());
    }

}
