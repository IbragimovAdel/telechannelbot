package bots;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Cinema {

    private final static String url = "https://www.kinopoisk.ru/";

    public static String getInfo() throws IOException {

        Document document = Jsoup.connect(url).get();
        Elements block = document.getElementsContainingOwnText("Сегодня в кино").parents().get(0).getElementsByTag("dd");
        String result = "Сегодня в кино \uD83D\uDCFD️\n\n";
        for(int i=0;i<5;i++){
            result += (i+1)+". " + block.get(i).getElementsByClass("title").text()+"\n";
        }

        return result;
    }
}
