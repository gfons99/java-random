package org.frgm.sat;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class XMLFormatter {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter the path of the XML file: ");
            String inputFilePath = reader.readLine();
//            System.out.print("Enter the path for the formatted output file: ");
//            String outputFilePath = reader.readLine();
            String outputFilePath = inputFilePath + ".fixed.xml";

            formatXML(inputFilePath, outputFilePath);
            System.out.println("Formatted XML saved to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    public static void formatXML(String inputFilePath, String outputFilePath) {
        try {
            // Parse XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(inputFilePath));
            document.normalize();

            // Set up transformer for pretty printing
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // Write formatted XML to file
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(outputFilePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            System.err.println("Error formatting XML: " + e.getMessage());
        }
    }
}

