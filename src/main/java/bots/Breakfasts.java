package bots;

import java.io.File;
import java.util.Date;

public class Breakfasts {

    public static File getImage(){

        Date date = new Date();
        int day = date.getDay();
        if(day%3==0) day = 1;
        else if(day%3==1) day = 2;
        else if(day%3==2) day = 3;
        File file = new File("src/main/resources/img/breakfasts/food"+day+".png");
        return file;
    }

}
