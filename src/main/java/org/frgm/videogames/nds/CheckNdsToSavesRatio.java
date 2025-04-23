package org.frgm.videogames.nds;

import java.io.File;
import java.util.Scanner;

public class CheckNdsToSavesRatio {
    public static void main(String[] args) {
        // Prompt the user to enter a directory
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the directory path: ");
        String directoryPath = scanner.nextLine();

        // Create a File object for the directory
        File directory = new File(directoryPath);

        // Check if the entered path is a directory
        if (directory.isDirectory()) {
            // List all files in the directory
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".sav"));

            // Check for each .sav file if a corresponding .nds file exists
            if (files != null) {
                for (File file : files) {
                    String savFileName = file.getName();
                    String ndsFileName = savFileName.replace(".sav", ".nds");
                    File ndsFile = new File(directory, ndsFileName);

                    // If .nds file does not exist, print a message with the .sav file name
                    if (!ndsFile.exists()) {
                        System.out.println("Missing .nds file for: " + savFileName);
                    }
                }
            }
        } else {
            System.out.println("The entered path is not a directory.");
        }

        scanner.close();
    }
}

