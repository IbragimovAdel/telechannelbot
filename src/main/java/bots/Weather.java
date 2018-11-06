package bots;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    private final static String url = "https://api.weatherbit.io/v2.0/forecast/daily?key=f63f4a53db38497abd1b88b3973a0757&lang=ru&days=1&city=Kazan&country=ru";

    public static String result;

    public static String getWeather() throws MalformedURLException, IOException {
        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod("GET");
        connection.connect();

        StringBuffer response = new StringBuffer();
        Scanner in;

        if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){

            in = new Scanner(new InputStreamReader(connection.getInputStream()));

            while(in.hasNext()){
                response.append(in.nextLine());
            }

        }else {
            System.out.println("Error: "+connection.getResponseCode()+", "+connection.getResponseMessage());
        }
        if(connection!=null){
            connection.disconnect();
        }

        JSONObject jsonObject = new JSONObject(response.toString());
        jsonObject = jsonObject.getJSONArray("data").getJSONObject(0);

        String weather = "";
        int code = jsonObject.getJSONObject("weather").getInt("code");
        if(code>=200&&code<300){
            weather = "⛈️";
        } else if(code>=300&&code<600) weather="\uD83C\uDF27️";
        else if(code >=600 && code <700) weather="\uD83C\uDF28️";
        else if(code >= 700 && code <800) weather="\uD83C\uDF2B️";
        else if(code==800) weather="☀️";
        else if(code >= 801 && code <804) weather="⛅";
        else if(code == 804) weather="☁️";
        else weather="\uD83C\uDF27️";

        String result = "Погода в городе \"Казань\" на "+jsonObject.getString("valid_date")+"\n\n"+"Температура воздуха от "+Double.toString(jsonObject.getDouble("min_temp"))+"°C до " + Double.toString(jsonObject.getDouble("max_temp"))+"°C \uD83C\uDF21️"+"\n"+jsonObject.getJSONObject("weather").getString("description")+weather+"\n\n"+"Удачного дня \uD83D\uDC4D";

        return result;
    }

}
