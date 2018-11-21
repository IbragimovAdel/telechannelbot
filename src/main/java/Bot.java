import bots.*;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

public class Bot extends TelegramLongPollingBot {

    Timer timer;

    public Bot(){
        
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                sendMessage(update.getMessage().getText(), update.getMessage().getChatId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMsg(String text) {
        Bot bot = Main.getBot();
        try {
            bot.sendMessage(text, (long) 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text, Long chatId) throws TelegramApiException, InterruptedException, IOException, SAXException {

        boolean send = true;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(BotSettings.CHANNEL_ID);
        if (text.equals("/weather")) {
            try {
                text = Weather.getWeather();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (text.equals("/news")) {
            try {
                text = LocalNews.getNews();
                sendMessage.disableWebPagePreview();
                sendMessage.setParseMode("Markdown");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (text.equals("/flashsale")) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(BotSettings.CHANNEL_ID);
            sendPhoto.setNewPhoto(FlashSales.getImage());
            sendPhoto(sendPhoto);
            send = false;
        } else if (text.equals("/poetry")) {
            text = Poetry.getInfo();
        } else if (text.equals("/breakfast")) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(BotSettings.CHANNEL_ID);
            sendPhoto.setNewPhoto(Breakfasts.getImage());
            sendPhoto(sendPhoto);
            send = false;
        } else if (text.equals("/autonews")) {
            text = AutoNews.getNews();
            sendMessage.disableWebPagePreview();
            sendMessage.setParseMode("Markdown");
        } else if (text.equals("/cinema")) {
            text = Cinema.getInfo();
        } else if (text.equals("/autonews")) {
            text = AutoNews.getNews();
            sendMessage.disableWebPagePreview();
            sendMessage.setParseMode("Markdown");
        } else if (text.contains("/channel")) {
            if (text.split(" ")[1].equals("Admin123")) BotSettings.setChannelId(text.split(" ")[2]);
            text = "Адрес канала изменен.";
            sendMessage.setChatId(chatId);
        } else if (text.equals("/history")) {
            text = History.getInfo();
        } else if (text.equals("/bot")){
            text = F1bot.getInfo();
        } else if (text.contains("/test")) {
            timer = new Timer();
            String[] cmd;
            cmd = text.split(" ");
            Date date = new Date();
            date.setHours(Integer.parseInt(cmd[1]) - 3);
            date.setMinutes(Integer.parseInt(cmd[2]));
            date.setSeconds(0);
            System.out.println(date.toString());
            timer.schedule(new TT("/news"), date, 60000);
            timer.schedule(new TT("/flashsale"), date, 60000);
            text = "Тестирование запущено";
            sendMessage.setChatId(chatId);
        } else if (text.equals("/stop")) {
            if(timer!=null)timer.cancel();
            text = "Бот остановлен";
            sendMessage.setChatId(chatId);
        } else if (text.equals("/start")) {              //START OF THE BOT

            if(timer==null) timer = new Timer();
            else timer.cancel();

            long p = 86400000;
            Date date = new Date();

            date.setHours(4);
            date.setMinutes(0);
            date.setSeconds(0);
            System.out.println(date);

            date.setHours(4);
            timer.schedule(new TT("/poetry"),date,p);
            date.setMinutes(30);
            timer.schedule(new TT("/weather"),date,p);
            date.setMinutes(35);
            timer.schedule(new TT("/history"),date,p);
            date.setMinutes(50);
            timer.schedule(new TT("/flashsale"),date,p);
            date.setMinutes(55);
            timer.schedule(new TT("/bot"),date,p);
            date.setHours(5);date.setMinutes(0);
            timer.schedule(new TT("/news"),date,p);
            date.setMinutes(30);
            timer.schedule(new TT("/breakfast"),date,p);
            date.setHours(9);date.setMinutes(0);
            timer.schedule(new TT("/autonews"),date,p);
            date.setHours(13);date.setMinutes(0);
            timer.schedule(new TT("/cinema"),date,p);

            text = "Бот запущен";
            System.out.println("Бот запущен!");
            sendMessage.setChatId(chatId);
            if(chatId==0) send = false;
        } else {
            sendMessage.setChatId(chatId);
            text = "Такой команды не существует";
        }
        sendMessage.setText(text);
        if (send) sendMessage(sendMessage);
    }

    public static Bot getBot() {
        return new Bot();
    }

    public String getBotUsername() {
        return BotSettings.BOT_USERNAME;
    }

    public String getBotToken() {
        return BotSettings.BOT_TOKEN;
    }
}
