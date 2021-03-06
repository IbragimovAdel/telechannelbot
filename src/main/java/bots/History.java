package bots;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

public class History {

    public static String getInfo() throws IOException {

        Date date = new Date();
        if(date.getMonth()==2&&date.getDate()==29) return null;
        String month = "";
        String key = "";
        switch (date.getMonth()+1){
            case 1:
                month = "yanvarya";
                key = "января";
                break;
            case 2:
                month = "fevralya";
                key = "февраля";
                break;
            case 3:
                month = "marta";
                key = "марта";
                break;
            case 4:
                month = "aprelya";
                key = "апреля";
                break;
            case 5:
                month = "maya";
                key = "мая";
                break;
            case 6:
                month = "iyunya";
                key = "июня";
                break;
            case 7:
                month = "iyulya";
                key = "июля";
                break;
            case 8:
                month = "avgusta";
                key = "августа";
                break;
            case 9:
                month = "sentyabrya";
                key = "сентября";
                break;
            case 10:
                month = "oktyabrya";
                key = "октября";
                break;
            case 11:
                month = "noyabrya";
                key = "ноября";
                break;
            case 12:
                month = "dekabrya";
                key = "декабря";
                break;
        }

        String result="\uD83D\uDD25 "+date.getDate()+" "+key+" в истории \uD83D\uDD25\n\n";

        String url = generateUrl(Integer.toString(date.getDate()),month);
        Document document = Jsoup.connect(url).get();
        Elements block = document.getElementsByTag("body").get(0).getElementsContainingOwnText(date.getDate()+" "+key+" в Истории").get(0).parent().parent().parent().parent().child(1).child(0).children();
        if(block.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                Element element = block.get(i);
                result += element.text() + "\n\n";
            }
        } else result = "-1";

        return result;
    }

    private static String generateUrl(String date, String month){

        return "https://ruspekh.ru/events/item/" + date + "-" + month + "-v-istorii";

    }

}
