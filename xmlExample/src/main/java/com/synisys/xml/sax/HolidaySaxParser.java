package com.synisys.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Astghik since 7/29/2017.
 */
public class HolidaySaxParser extends DefaultHandler {

    private static List<String> simpleElements = new ArrayList<>(Arrays.asList("name", "capital", "location", "url", "departureDate", "arrivalDate"));
    private static Map<String, String> compositeElements = new HashMap<>();

    static {
        compositeElements.put("countriesToVisit", "Countries to see: ");
        compositeElements.put("topSightsToSee", "Top things to see: ");
        compositeElements.put("country", "Country: ");
        compositeElements.put("sight", "Sight: ");
    }

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("holiday")) {
            if (attributes.getValue(0).equalsIgnoreCase("yes")) {
                System.out.println("Unfortunately my holiday is delayed :( But still let's have a look at the info.");
            } else {
                System.out.println("My holiday :) ");
            }
        }
        if (compositeElements.keySet().stream().collect(Collectors.toList()).contains(qName)) {
            System.out.print(compositeElements.get(qName));
        }
        if (simpleElements.contains(qName)) {
            System.out.print(qName + ": ");
        }
    }

    @Override
    public void characters(char ch[],
                           int start, int length) throws SAXException {
        System.out.print(new String(ch, start, length));
    }

}
