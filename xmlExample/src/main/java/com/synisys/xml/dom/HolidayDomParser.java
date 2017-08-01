package com.synisys.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Astghik since 7/29/2017.
 */
public class HolidayDomParser {

    private static List<String> simpleElements = new ArrayList<>(Arrays.asList("name", "capital", "location", "url", "departureDate", "arrivalDate"));
    private static Map<String, String> compositeElements = new HashMap<>();

    static {
        compositeElements.put("countriesToVisit", "Countries to see: ");
        compositeElements.put("topSightsToSee", "Top things to see: ");
        compositeElements.put("country", "Country: ");
        compositeElements.put("sight", "Sight: ");
    }

    public static void parseXmlDocument(File file) throws ParserConfigurationException, IOException, SAXException {
        checkIsXmlValid(file);

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document document = docBuilder.parse(file);
        document.getDocumentElement().normalize();

        NodeList holidays = document.getElementsByTagName("holiday");
        for (int i = 0; i < holidays.getLength(); i++) {
            Node node = holidays.item(i);
            parseNode(node);
        }

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        try {
            System.out.print("I am going to visit in " + ((Node) xpath.compile("/holiday/countriesToVisit/country[1]")
                    .evaluate(document, XPathConstants.NODE)).getChildNodes().item(1).getTextContent() + " " +
                    ((NodeList) xpath.compile("/holiday/countriesToVisit/country[1]/topSightsToSee")
                            .evaluate(document, XPathConstants.NODESET)).getLength() + " sights");
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    private static void parseNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String tagName = element.getTagName();
            if (element.getTagName().equalsIgnoreCase("holiday")) {
                if (element.getAttribute("isDelayed").equalsIgnoreCase("yes")) {
                    System.out.println("Unfortunately my holiday is delayed :( But still let's have a look at the info.");
                } else {
                    System.out.println("My upcoming holiday :) ");
                }
                for (int j = 0; j < element.getChildNodes().getLength(); j++) {
                    parseNode(element.getChildNodes().item(j));
                }
            }
            if (compositeElements.keySet().stream().collect(Collectors.toList()).contains(tagName)) {
                System.out.println(compositeElements.get(tagName));
                for (int i = 0; i < element.getChildNodes().getLength(); i++) {
                    parseNode(element.getChildNodes().item(i));
                }
            }
            if (simpleElements.contains(tagName)) {
                System.out.println(tagName + ": " + element.getTextContent());
            }
        }
    }

    private static void checkIsXmlValid(File file) {
        try {
            File xsdFile = new File("src/main/resources/holidayXsd");
            SchemaFactory xsdSchemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema xsdSchema = xsdSchemaFactory.newSchema(xsdFile);
            Validator xsdValidator = xsdSchema.newValidator();
            xsdValidator.validate(new StreamSource(file));

            System.out.println("Provided xml is valid");
        } catch (SAXException e) {
            System.out.println("Provided xml is invalid");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


