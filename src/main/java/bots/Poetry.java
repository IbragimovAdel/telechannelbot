package bots;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Poetry {

    private final static String url = "https://www.askbooka.ru/stihi/author-of-the-poem";

    public static String getInfo() throws IOException {

        Document document = Jsoup.connect(url).get();
        Element block = document.getElementsContainingOwnText("Сегодня в кино").parents().get(0);
        String result = "Сегодня в кино \uD83D\uDCFD️\n\n";
        for(int i=1;i<6;i++){
            result += i+". " + block.children().get(i).getElementsByTag("s").text()+"\n";
        }

        return result;
    }

}
