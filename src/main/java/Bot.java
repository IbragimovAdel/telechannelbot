import bots.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Bot extends TelegramLongPollingBot {

    Timer timer;

    public Bot() {
        try {
            sendMessage("/start",(long) 0);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            int userID = update.getMessage().getFrom().getId();
            if(userID!=259993783) return;
            try {
                sendMessage(update.getMessage().getText(), update.getMessage().getChatId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String text) {
        try {
            sendMessage(text, (long) 0);
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
                sendMessage.disableNotification();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (text.equals("/news")) {
            try {
                text = LocalNews.getNews();
                sendMessage.disableWebPagePreview();
                sendMessage.setParseMode("Markdown");
                sendMessage.disableNotification();
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
            sendMessage.disableNotification();
        } else if (text.equals("/breakfast")) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(BotSettings.CHANNEL_ID);
            sendPhoto.setNewPhoto(Breakfasts.getImage());
            sendPhoto.disableNotification();
            sendPhoto(sendPhoto);
            send = false;
        } else if (text.equals("/autonews")) {
            text = AutoNews.getNews();
            sendMessage.disableWebPagePreview();
            sendMessage.setParseMode("Markdown");
        } else if (text.equals("/cinema")) {
            text = Cinema.getInfo();
            sendMessage.disableNotification();
        } else if (text.equals("/autonews")) {
            text = AutoNews.getNews();
            sendMessage.disableWebPagePreview();
            sendMessage.setParseMode("Markdown");
        } else if (text.contains("/channel")) {
            if (text.split(" ")[1].equals("Admin123")) BotSettings.setChannelId(text.split(" ")[2]);
            text = "Адрес канала изменен.";
            sendMessage.setChatId(chatId);
        } else if (text.equals("/history")) {
            sendMessage.disableNotification();
            text = History.getInfo();
        } else if (text.equals("/bot")){
            text = F1bot.getInfo();
        } else if (text.contains("/test")) {
            timer = new Timer();
            String[] cmd;
            cmd = text.split(" ");
            Date date = new Date();
            Date currentDate = new Date();
            date.setHours(Integer.parseInt(cmd[1]));
            date.setMinutes(Integer.parseInt(cmd[2]));
            date.setSeconds(0);
            System.out.println(date.toString());
            if(currentDate.before(date)) timer.scheduleAtFixedRate(new TT("/news",this), date, 60000);
            if(currentDate.before(date)) timer.scheduleAtFixedRate(new TT("/flashsale",this), date, 60000);
            text = "Тестирование запущено";
            sendMessage.setChatId(chatId);
        } else if (text.equals("/stop")) {
            if(timer!=null)timer.cancel();
            text = "Бот остановлен";
            sendMessage.setChatId(chatId);
        } else if (text.equals("/start")) {              //START OF THE BOT

            timer = new Timer();

            Date currentDate = new Date();
            Date date = new Date();
            date.setMinutes(0);
            date.setSeconds(0);
            System.out.println(date);

            date.setHours(4);
            if(currentDate.before(date)) timer.schedule(new TT("/poetry",this),date);
            date.setMinutes(30);
            if(currentDate.before(date)) timer.schedule(new TT("/weather",this),date);
            date.setMinutes(35);
            if(currentDate.before(date)) timer.schedule(new TT("/history",this),date);
            date.setMinutes(50);
            if(currentDate.before(date)) timer.schedule(new TT("/flashsale",this),date);
            date.setMinutes(55);
            if(currentDate.before(date)) timer.schedule(new TT("/bot",this),date);
            date.setHours(5);date.setMinutes(0);
            if(currentDate.before(date)) timer.schedule(new TT("/news",this),date);
            date.setMinutes(30);
            if(currentDate.before(date)) timer.schedule(new TT("/breakfast",this),date);
            date.setHours(9);date.setMinutes(0);
            if(currentDate.before(date)) timer.schedule(new TT("/autonews",this),date);
            date.setHours(13);date.setMinutes(0);
            if(currentDate.before(date)) timer.schedule(new TT("/cinema",this),date);
            date.setDate(date.getDate()+1);date.setHours(1);date.setMinutes(0);date.setSeconds(0);
            timer.schedule(new TT("/start",this),date);

            text = "Бот запущен";
            sendMessage.setChatId(chatId);
            if(chatId==0) send = false;
        } else {
            sendMessage.setChatId(chatId);
            text = "Такой команды не существует";
        }
        sendMessage.setText(text);
        if (send) sendMessage(sendMessage);
    }

    public Bot getBot(){
        return this;
    }

    public String getBotUsername() {
        return BotSettings.BOT_USERNAME;
    }

    public String getBotToken() {
        return BotSettings.BOT_TOKEN;
    }

    public class TT extends TimerTask {

        String cmd;
        Bot bot;

        public TT(String cmd,Bot bot){

            this.cmd = cmd;
            this.bot = bot;

        }

        public void run() {
            try {
                bot.sendMessage(cmd, (long) 0);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }
}
