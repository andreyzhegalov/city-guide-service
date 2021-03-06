package cityguide.telegrambot.telegram.bot;

import java.util.Optional;
import java.util.function.Function;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler extends Function<Message, Optional<String>> {
    int MAX_RESPONSE_MESSAGE_LENGTH = 4096;
}
