package bots;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class AutoNews {

    private final static String url = "https://motor.ru/exports/rss";

    public static String getNews() throws IOException {
        String result = "АвтоНовости \uD83D\uDE97\n\n";

        Document document = Jsoup.connect(url).get();
        Elements items = document.getElementsByTag("item");

        for(int i=0;i<5;i++){
            Element item = items.get(i);
            String title = item.getElementsByTag("title").text();
            String link = item.getElementsByTag("link").text();
            result += ("\""+title+"\"\n[Подробнее]("+link+")\n\n");
        }

        return result;
    }


}
