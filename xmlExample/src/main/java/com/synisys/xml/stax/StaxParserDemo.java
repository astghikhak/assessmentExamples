package com.synisys.xml.stax;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Astghik since 7/29/2017.
 */
public class StaxParserDemo {

    private static List<String> simpleElements = new ArrayList<>(Arrays.asList("name", "capital", "location", "url", "departureDate", "arrivalDate"));
    private static Map<String, String> compositeElements = new HashMap<>();

    static {
        compositeElements.put("countriesToVisit", "Countries to see: ");
        compositeElements.put("topSightsToSee", "Top things to see: ");
        compositeElements.put("country", "Country: ");
        compositeElements.put("sight", "Sight: ");
    }


    public static void main(String[] args) {
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(
                            new FileReader("src/main/resources/holidayXml"));

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        if (qName.equalsIgnoreCase("holiday")) {
                            if (startElement.getAttributeByName(new QName("isDelayed")).getValue().equalsIgnoreCase("yes")) {
                                System.out.println("Unfortunately my holiday is delayed :( But still let's have a look at the info.");
                            } else {
                                System.out.println("This can not be true :) ");
                            }
                        }
                        if (compositeElements.keySet().stream().collect(Collectors.toList()).contains(qName)) {
                            System.out.print(compositeElements.get(qName));
                        }
                        if (simpleElements.contains(qName)) {
                            System.out.print(qName + ": ");
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        System.out.print(characters.getData());
                        break;
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
