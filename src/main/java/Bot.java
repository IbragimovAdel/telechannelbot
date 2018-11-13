import bots.*;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

public class Bot extends TelegramLongPollingBot {

    Timer timer;

    private boolean isStopped;

    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&&update.getMessage().hasText()){
            try {
                sendMessage(update.getMessage().getText(),update.getMessage().getChatId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendMsg(String text){
        Bot bot = new Bot();
        try {
            bot.sendMessage(text,(long) 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text, Long chatId) throws TelegramApiException, InterruptedException, IOException, SAXException {
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
                text = News.getNews();
                sendMessage.disableWebPagePreview();
                sendMessage.setParseMode("Markdown");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (text.contains("/flashsale")) {
            int d = -1;
            String[] cmd = text.split(" ");
            if(cmd.length == 2 && cmd[1].charAt(0)>=49 && cmd[1].charAt(0)<=55) d = Integer.parseInt(cmd[1]);
            String s = FlashSales.getFlashSalesText(d);
            String url = FlashSales.getFlashSalesUrl(d);
            sendMessage.setParseMode("HTML");
            sendMessage.enableWebPagePreview();
            if (s.equals("") && url.equals("")) text = "На данный момент акций нет \uD83D\uDE22";
            else if (url.equals("")) text = s;
            else if (s.equals("")) text = "\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\n<a href=\"" + url+"\">&#8205;</a>";
            else text = s + "\n\n" + "<a href=\"" + url + "\"></a>";
        } else if(text.contains("/setfstext")){
            FlashSales.setFlashSalesText(text);
            sendMessage.setChatId(chatId);
            if(text.split(" ").length == 1) text = "Недостаточно аргументов";
            else {
                text = "Текст успешно изменен";
            }
        } else if(text.contains("/setfsurl")) {
            FlashSales.setFlashSalesUrl(text);
            sendMessage.setChatId(chatId);
            if(text.split(" ").length == 1) text = "Недостаточно аргументов";
            else {
                text = "Ссылка успешно изменена";
            }
        } else if(text.equals("/autonews")){
            text = AutoNews.getNews();
            sendMessage.disableWebPagePreview();
            sendMessage.setParseMode("Markdown");
        } else if(text.equals("/cinema")){
            text = Cinema.getInfo();
        } else if(text.contains("/channel")){
            if(text.split(" ")[1].equals("Admin123")) BotSettings.setChannelId(text.split(" ")[2]);
            text = "Адрес канала изменен.";
            sendMessage.setChatId(chatId);
        } else if (text.contains("/test")){
            timer = new Timer();

            String[] cmd;
            cmd = text.split(" ");
            Date date = new Date();
            date.setHours(Integer.parseInt(cmd[1]));
            date.setMinutes(Integer.parseInt(cmd[2]));
            date.setSeconds(0);
            System.out.println(date.toString());
            timer.schedule(new WeatherTT(),date,60000);
            timer.schedule(new NewsTT(),date,60000);
            text = "Тестирование запущено";
            sendMessage.setChatId(chatId);
        } else if (text.equals("/stop")){
            timer.cancel();
            isStopped = true;
            text = "Тестирование остановлено";
            sendMessage.setChatId(chatId);
        }
        else {
            sendMessage.setChatId(chatId);
            text = "Такой команды не существует";
        }
        sendMessage.setText(text);
        sendMessage(sendMessage);
    }

    public static Bot getBot(){
        return new Bot();
    }

    public String getBotUsername() {
        return BotSettings.BOT_USERNAME;
    }

    public String getBotToken() {
        return BotSettings.BOT_TOKEN;
    }
}
