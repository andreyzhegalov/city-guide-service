package cityguide.backend;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cityguide.backend.config.BackendConfig;
import cityguide.backend.service.TelegramBotService;

public class TelegramBot {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BackendConfig.class, TelegramBotService.class);
        final var telegramBotService = ctx.getBean(TelegramBotService.class);
        telegramBotService.start();
    }
}

