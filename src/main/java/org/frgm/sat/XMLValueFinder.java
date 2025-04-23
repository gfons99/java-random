package org.frgm.sat;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLValueFinder {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
//            System.out.print("Enter the XML file path: ");
//            String filePath = reader.readLine();
            String filePath = "E:\\Otros\\MOM\\SAT\\DECLARACIONES\\2025\\01-recibidas\\0C61240A-59BA-488F-BEED-6A79ADE006A9.xml";

            System.out.println(getValuesFromXmlFile(filePath));

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static List<String> getValuesFromXmlFile(String filePath) {
        List<String> values = new ArrayList<>();

        values.add(findValueInXML(filePath, "TimbreFiscalDigital", "UUID").toUpperCase());

        values.add("\"" + findValueInXML(filePath, "Emisor", "Nombre") + "\"");
        values.add(findValueInXML(filePath, "Comprobante", "Fecha"));
        values.add(findValueInXML(filePath, "TimbreFiscalDigital", "RfcProvCertif"));
        values.add(findValueInXML(filePath, "Comprobante", "Total"));
        values.add(findValueInXML(filePath, "Comprobante", "SubTotal"));
        values.add(findValueInXML(filePath, "Impuestos", "TotalImpuestosTrasladados"));

        values.add(replaceNumberWithString(findValueInXML(filePath, "Comprobante", "FormaPago")));
        values.add(findValueInXML(filePath, "Comprobante", "MetodoPago"));
        values.add(findValueInXML(filePath, "Comprobante", "Moneda"));

        return values;
    }

    public static String findValueInXML(String filePath, String tagName, String attributeKey) {
        try {
            // Load and parse the XML file
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Handle namespaces
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();

            // Find the specified tag
            NodeList nodeList = document.getElementsByTagNameNS("*", tagName);
//            System.out.println(tagName + " : " + nodeList.getLength());

            String returnValue = null;
            if (nodeList.getLength() == 1) {
                Element element = (Element) nodeList.item(0);
                returnValue = element.getAttribute(attributeKey);
            }
            else {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element element = (Element) nodeList.item(i);
                    if (!element.getAttribute(attributeKey).isEmpty()) {
                        returnValue = element.getAttribute(attributeKey);
                    }
                }
                if (attributeKey.equals("TotalImpuestosTrasladados") && returnValue == null) {
                    returnValue = "0.00";
                }
            }
            return returnValue;

        } catch (Exception e) {
            System.err.println("Error processing XML: " + e.getMessage());
        }
        return null;
    }

    public static String replaceNumberWithString(String input) {
        // Define a map with the number-to-string replacements
        Map<String, String> replacements = new HashMap<>();
        replacements.put("01", "Efectivo");
        replacements.put("02", "Cheque nominativo");
        replacements.put("03", "Transferencia electrónica de fondos");
        replacements.put("04", "Tarjeta de crédito");
        replacements.put("05", "Monedero electrónico");
        replacements.put("06", "Dinero electrónico");
        replacements.put("08", "Vales de despensa");
        replacements.put("12", "Dación en pago");
        replacements.put("13", "Pago por subrogación");
        replacements.put("14", "Pago por consignación");
        replacements.put("15", "Condonación");
        replacements.put("17", "Compensación");
        replacements.put("23", "Novación");
        replacements.put("24", "Confusión");
        replacements.put("25", "Remisión de deuda");
        replacements.put("26", "Prescripción o caducidad");
        replacements.put("27", "A satisfacción del acreedor");
        replacements.put("28", "Tarjeta de débito");
        replacements.put("29", "Tarjeta de servicios");
        replacements.put("30", "Aplicación de anticipos");
        replacements.put("99", "Por definir");

        // Replace the number with the corresponding string (if available)
        return replacements.getOrDefault(input, input);  // Return the input if no replacement is found
    }
}
