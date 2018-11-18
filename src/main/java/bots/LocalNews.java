package bots;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LocalNews {

    private final static String url = "https://rt.rbc.ru/tatarstan";

    public static String getNews() throws IOException {
        String result = "Новости \uD83D\uDCF0 \n\n";
        Document document = Jsoup.connect(url).get();

        Elements news = document.getElementsByClass("g-o-category-title").get(0).parent().child(1).children();

        for(int i=0;i<5;i++){
            Element item = news.get(i).child(0);
            String url = item.attr("href");
            String text = item.child(0).text();
            result += ("\""+text+"\"\n[Подробнее]("+url+")\n\n");
        }

        return result;
    }
}
