package com.synisys.xml.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

/**
 * Created by Astghik since 7/29/2017.
 */
public class SaxParserDemo {

    public static void main(String[] args) {
        try{
            File file = new File("src/main/resources/holidayXml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            HolidaySaxParser holidaySaxParser = new HolidaySaxParser();
            saxParser.parse(file, holidaySaxParser);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
