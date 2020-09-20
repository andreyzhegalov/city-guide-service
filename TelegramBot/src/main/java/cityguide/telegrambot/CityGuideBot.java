package cityguide.telegrambot;

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

    public CityGuideBot(String token) {
        this.botToken = token;
    }

    @Override
    public String getBotUsername() {
        return "CityGuide2020Bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                LOG.info("Recive message {}", message.toString());
                sendMessage(update.getMessage().getChatId().toString(), message.getText());
            }
            if (message.hasLocation()) {
                LOG.info("Recive location {}", message.getLocation().toString());
            }
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
