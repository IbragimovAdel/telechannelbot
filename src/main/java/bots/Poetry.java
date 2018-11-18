package bots;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Poetry {

    private final static String url = "https://www.askbooka.ru/stihi/author-of-the-poem";

    public static String getInfo() throws IOException {

        Document document = Jsoup.connect(url).get();
        Elements block = document.getElementsByTag("body").get(0).getElementsContainingOwnText("Угадайте автора стихотворения").get(0).parent().parent().parent().child(1).child(0).children();
        String result = "";
        String author = block.get(0).parent().parent().parent().child(2).getElementsContainingOwnText("Автор:").get(0).child(0).text();

        for(int i=0;i<block.size();i++){
            Element element = block.get(i);
            String s = element.toString();
            s = s.substring(3,s.length()-4);
            for(int b=0;b<s.length();b++){
                if(s.charAt(b)=='<'){
                    result+="\n";
                } else if(s.charAt(b)!='b'&&s.charAt(b)!='r'&&s.charAt(b)!='>') result += s.charAt(b);
            }
            result+="\n\n";
            result=result.substring(0,result.length()-2);
        }

        result+=("Автор: "+author);
        return result;
    }

}
