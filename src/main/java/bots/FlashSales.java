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

public class FlashSales {

    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;
    private static Document document;

    public static void load(){

        factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(new File("src/main/resources/FlashSales.xml"));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getFlashSalesText(){

        Calendar calendar = Calendar.getInstance();
        int d = calendar.get(Calendar.DAY_OF_WEEK);
        NodeList nodeList = document.getElementsByTagName("day");
        Element element = (Element) nodeList.item(d-1);
        String result = element.getElementsByTagName("sale").item(0).getTextContent();

        return result;
    }

    public static void setFlashSalesText(String s) {

        String[] cmd = s.split(" ");

        if(cmd.length == 1) return;
        else if(cmd.length == 2 && cmd[1].charAt(0)>=49 && cmd[1].charAt(0)<=55) {
            int d = Integer.parseInt(cmd[1]);

            NodeList nodeList = document.getElementsByTagName("day");
            Element element = (Element) nodeList.item(d-1);
            element.getElementsByTagName("sale").item(0).setTextContent("");
        } else if(cmd.length > 2) {
            int d = Integer.parseInt(cmd[1]);

            NodeList nodeList = document.getElementsByTagName("day");
            Element element = (Element) nodeList.item(d-1);
            String text = "";
            for(int i=0;i<cmd.length-2;i++){
                text += (cmd[i+2] + " ");
            }
            element.getElementsByTagName("sale").item(0).setTextContent(text);
        } else return;

    }

    public static void setFlashSalesUrl(String s) {

        String[] cmd = s.split(" ");

    }

    public static String getFlashSalesUrl(){

        Calendar calendar = Calendar.getInstance();
        int d = calendar.get(Calendar.DAY_OF_WEEK);
        NodeList nodeList = document.getElementsByTagName("day");
        Element element = (Element) nodeList.item(d-1);
        String result = element.getElementsByTagName("url").item(0).getTextContent();

        return result;
    }

}
