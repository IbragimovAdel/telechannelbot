package bots;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AutoNews {

    private final static String url = "https://motor.ru/exports/rss";

    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Document document;

    public static void load(){

        factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    public static String getNews() throws IOException, SAXException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

        StringBuffer response = new StringBuffer();
        Scanner in;
        int j = 0;

        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {

            in = new Scanner(new InputStreamReader(connection.getInputStream()));

            while (in.hasNext() && j<5) {
                String s = in.nextLine();
                if (s.contains("/item")) j++;
                response.append(s);
            }

        } else {
            System.out.println("Error: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
        }
        if (connection != null) {
            connection.disconnect();
        }

        String result = "АвтоНовости \uD83D\uDCF0 \n\n";
        document = builder.parse(new InputSource(new StringReader(response.toString()+"</channel></rss>")));
        System.out.println(document.toString());

        for(int i=0;i<5;i++){
            Element element = (Element) document.getElementsByTagName("rss").item(0).getChildNodes().item(0).getChildNodes().item(i);
            result += "\"" + element.getElementsByTagName("title").item(0).getTextContent() + "\"\n[Подробнее](" + element.getElementsByTagName("link").item(0).getTextContent()+")\n\n";
        }

        return result;
    }

    public static void main(String[] args) throws IOException, SAXException {
        load();
        System.out.println(getNews());
    }


}
