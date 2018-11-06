package bots;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class News {

    private final static String url = "https://newsapi.org/v2/top-headlines?sources=rbc&apiKey=1c3ebc9d78a84c8290d7eb261c05a007&pageSize=5";

    public static String getNews() throws IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

        StringBuffer response = new StringBuffer();
        Scanner in;

        if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {

            in = new Scanner(new InputStreamReader(connection.getInputStream()));

            while (in.hasNext()) {
                response.append(in.nextLine());
            }

        } else {
            System.out.println("Error: " + connection.getResponseCode() + ", " + connection.getResponseMessage());
        }
        if (connection != null) {
            connection.disconnect();
        }

        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray jsonArray = jsonObject.getJSONArray("articles");
        String result="Новости \uD83D\uDCF0 \n\n";

        for(int i=0;i<5;i++){
            jsonObject = jsonArray.getJSONObject(i);
            result+="\""+jsonObject.getString("title")+"\"\n[Подробнее]("+jsonObject.getString("url")+")\n\n";
        }

        return result;
    }

}
