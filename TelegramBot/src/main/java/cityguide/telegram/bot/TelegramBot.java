package cityguide.telegram.bot;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger LOG = LogManager.getLogger(TelegramBot.class);
    private final String botToken;
    private final String botName;
    private MessageHandler messageHandler;

    public TelegramBot(String botName, String token) {
        this.botToken = token;
        this.botName = botName;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (messageHandler == null) {
                return;
            }
            final var mayBeResponse = messageHandler.apply(message);
            if (mayBeResponse.isEmpty()) {
                return;
            }
            final var messageList = splitMessage(mayBeResponse.get().toString());
            final var chatId = message.getChatId().toString();
            for (final var partOfMessage : messageList) {
                sendMessage(chatId, partOfMessage);
            }
        }
    }

    public void sendMessage(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            LOG.error("Exception: {}", e.toString());
        }
    }

    private List<String> splitMessage(String message) {
        final var messageList = new ArrayList<String>();
        for (final String chunk : Splitter.fixedLength(MessageHandler.MAX_RESPONSE_MESSAGE_LENGTH).split(message)) {
            messageList.add(chunk);
        }
        return messageList;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
