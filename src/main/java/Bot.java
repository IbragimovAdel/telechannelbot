import bots.FlashSales;
import bots.News;
import bots.Weather;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.MalformedURLException;

public class Bot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&&update.getMessage().hasText()){
            try {
                sendMessage(update.getMessage().getText(),update.getMessage().getChatId());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage(String text, Long chatId) throws TelegramApiException {
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
        } else if (text.equals("/flashsale")) {
            String s = FlashSales.getFlashSalesText();
            String url = FlashSales.getFlashSalesUrl();
            sendMessage.setParseMode("Markdown");
            if (s.equals("") && url.equals("")) text = "На данный момент акций нет \uD83D\uDE22";
            else if (url.equals("")) text = s;
            else if (s.equals("")) text = "[ ](" + url+")";
            else text = s + "\n\n" + "[](" + url + ")";
        } else if(text.contains("/setfstext")){
            FlashSales.setFlashSalesText(text);
            sendMessage.setChatId(chatId);
            text = "Текст успешно изменен";
            if(text.split(" ").length == 0) text = "Недостаточно аргументов";
        } else if(text.contains("/setfsurl")) {
            FlashSales.setFlashSalesUrl(text);
            sendMessage.setChatId(chatId);
            text = "Ссылка успешно изменена";
            if(text.split(" ").length == 0) text = "Недостаточно аргументов";
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
