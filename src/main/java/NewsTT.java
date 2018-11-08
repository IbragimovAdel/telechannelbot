import bots.News;

import java.io.IOException;
import java.util.TimerTask;

public class NewsTT extends TimerTask {

    public void run() {
        Bot.sendMsg("/news");
    }
}
