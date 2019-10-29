package bots;

import org.telegram.telegrambots.api.objects.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class FlashSales {

    public static File getImage(){

        Date date = new Date();
        int day = date.getDay()+1;
        File file = new File("src/main/resources/img/flashsales/fs"+day+".png");
        return file;
    }

}
