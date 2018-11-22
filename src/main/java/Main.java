import bots.FlashSales;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.xml.sax.SAXException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, SAXException, TelegramApiException, IOException {
        Bot bot = new Bot();
        System.out.println("Запуск...");
        bot.sendMessage("/start", (long) 0);
    }

}
