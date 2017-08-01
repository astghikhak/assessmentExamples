package com.synisys.xml.dom;

import java.io.File;

/**
 * Created by Astghik since 7/29/2017.
 */
public class DomParserDemo {

    public static void main(String[] args) {
        try{
            File file = new File("src/main/resources/holidayXml");
            HolidayDomParser.parseXmlDocument(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
