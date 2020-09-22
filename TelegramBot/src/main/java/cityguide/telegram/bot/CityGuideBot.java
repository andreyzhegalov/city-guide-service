package cityguide.telegram.bot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CityGuideBot extends TelegramLongPollingBot {
    private static final Logger LOG = LogManager.getLogger(CityGuideBot.class);
    private final String botToken;
    private MessageHandler messageHandler;

    public CityGuideBot(String token) {
        this.botToken = token;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public String getBotUsername() {
        return "CityGuide2020Bot";
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
            sendMessage(update.getMessage().getChatId().toString(), mayBeResponse.get().toString());
        }
    }

    public synchronized void sendMessage(String chatId, String s) {
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

    @Override
    public String getBotToken() {
        return botToken;
    }
}
