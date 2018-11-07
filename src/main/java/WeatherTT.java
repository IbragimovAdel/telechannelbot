import bots.Weather;

import java.io.IOException;
import java.util.TimerTask;

public class WeatherTT extends TimerTask {
    public void run() {
        try {
            Bot.sendMsg(Weather.getWeather());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
