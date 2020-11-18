package cityguide.telegrambot.telegram.bot;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final String botToken;
    private final String botName;
    private MessageHandler messageHandler;

    public TelegramBot(String botName, String token) {
        log.trace("TRACE Construct telegram bot " + botName);
        log.debug("DEBUG Construct telegram bot " + botName);
        log.info("INFO Construct telegram bot " + botName);
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
        if (!update.hasMessage()) {
            return;
        }
        if (messageHandler == null) {
            return;
        }
        final Message message = update.getMessage();
        final var mayBeResponse = messageHandler.apply(message);
        if (mayBeResponse.isEmpty()) {
            return;
        }
        final var messageList = splitMessage(mayBeResponse.get());
        final var chatId = message.getChatId().toString();
        for (final var partOfMessage : messageList) {
            final var messageForSend = prepareSendMessage(chatId, partOfMessage);
            sendMessage(messageForSend);
        }
    }

    public SendMessage prepareSendMessage(String chatId, String s) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        return sendMessage;
    }

    public void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Exception: {}", e.toString());
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
