package org.frgm.sat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVGenerator {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter the path for the directory that contains all XML invoices: ");
            String inputDirectoryPath = reader.readLine();

            // Write data rows
            List<String> xmlFilePaths = listXMLFiles(inputDirectoryPath) ;
            List<String> dataRowsWithComas =  new ArrayList<>();

            for (String xmlFilePath : xmlFilePaths) {
                List<String> values = XMLValueFinder.getValuesFromXmlFile(xmlFilePath);
                StringBuilder row = new StringBuilder();
                for (String value : values) {
                    row.append(value).append(",");
                }
                dataRowsWithComas.add(row.toString());
            }

            File baseDirectory = new File(inputDirectoryPath);
            String outputFilePath = baseDirectory.getParent() + "\\" + baseDirectory.getName() + ".csv";
            generateCSV(outputFilePath, dataRowsWithComas);
            System.out.println("Results file saved to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }



    }

    public static void generateCSV(String filePath, List<String> dataRowsWithComas) {
        // Define CSV headers
        String[] headers = {
            // Como es formalmente:
            "Folio Fiscal",
            "Nombre o Razón Social del Emisor",
            "Fecha de Emisión",
            "PAC que Certificó",
            // Como dice en el XML:
            "Total",
            "Subtotal",
            "TotalImpuestosTrasladados",
            "FormaPago",
            "MetodoPago",
            "Moneda"
        };

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"))) {
            // Write UTF-8 BOM (optional, for compatibility with Excel)
            writer.write('\uFEFF'); // Byte Order Mark (BOM) for UTF-8

            // Write headers
            writer.write(String.join(",", headers));
            writer.newLine();

            // Write data rows
            for (String row : dataRowsWithComas) {
                writer.write(row);
                writer.newLine();
            }

            System.out.println("CSV file created successfully at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    public static List<String> listXMLFiles(String directoryPath) {
        List<String> xmlFilePaths = new ArrayList<>();
        File directory = new File(directoryPath);

        // Check if the path is a valid directory
        if (!directory.isDirectory()) {
            System.out.println("Invalid directory: " + directoryPath);
            return xmlFilePaths; // Return empty list if directory is invalid
        }

        // List all .xml files in the directory
        File[] xmlFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (xmlFiles != null) {
            for (File file : xmlFiles) {
                xmlFilePaths.add(file.getAbsolutePath());
            }
        }

        return xmlFilePaths;
    }
}
