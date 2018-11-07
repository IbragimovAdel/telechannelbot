import bots.News;

import java.io.IOException;
import java.util.TimerTask;

public class NewsTT extends TimerTask {

    public void run() {
        try {
            Bot.sendMsg(News.getNews());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
