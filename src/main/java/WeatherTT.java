import bots.Weather;

import java.io.IOException;
import java.util.TimerTask;

public class WeatherTT extends TimerTask {
    public void run() {
        try {
            Bot.sendMsg("/weather");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
