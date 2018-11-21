import bots.FlashSales;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.xml.sax.SAXException;

import java.io.IOException;

public class Main {

    static Bot bot;

    public static void main(String[] args) throws InterruptedException, SAXException, TelegramApiException, IOException {
        bot = new Bot();
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
        System.out.println("Запуск...");
        bot.sendMessage("/start", (long) 0);
    }

    public static Bot getBot(){
        return bot;
    }

}
