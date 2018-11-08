import bots.Weather;

import java.io.IOException;
import java.util.TimerTask;

public class WeatherTT extends TimerTask {
    public void run() {
<<<<<<< HEAD
        Bot.sendMsg("/weather");
=======
        try {
            Bot.sendMsg("/weather");
        } catch (IOException e) {
            e.printStackTrace();
        }
>>>>>>> 3c760c6e16a742c6ea52c27af8c623205465d598
    }
}
