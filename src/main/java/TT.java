import java.util.TimerTask;

public class TT extends TimerTask {

    private String cmd;

    public TT(String cmd){
        this.cmd = cmd;
    }

    public void run() {
        Bot.sendMsg(cmd);
    }
}
