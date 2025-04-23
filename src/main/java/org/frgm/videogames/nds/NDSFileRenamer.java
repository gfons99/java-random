package org.frgm.videogames.nds;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import org.frgm.util.Colores;

public class NDSFileRenamer {

    private static final int GAME_CODE_OFFSET = 0x0C;
    private static final int GAME_CODE_LENGTH = 4;
    private static final int GAME_TITLE_OFFSET = 0x00;
    private static final int GAME_TITLE_LENGTH = 12;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("****************************************************************");
        System.out.println("Where to get the game data?:");
        System.out.println("https://www.gametdb.com/DS/Search");
        System.out.println("****************************************************************");

        // Ask user for the directory path
        System.out.print("Enter the directory path containing .nds files: ");
        String directoryPath = scanner.nextLine().trim();

        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory: " + directoryPath);
            return;
        }

        File[] ndsFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".nds"));

        if (ndsFiles == null || ndsFiles.length == 0) {
            System.out.println("No .nds files found in the directory: " + directoryPath);
            return;
        }

        for (File ndsFile : ndsFiles) {
            try (FileInputStream fis = new FileInputStream(ndsFile)) {
                byte[] header = new byte[0x200]; // Read the first 512 bytes (the header size)

                // Replacement for IOUtils.readFully
                int bytesRead = 0;
                while (bytesRead < header.length) {
                    int count = fis.read(header, bytesRead, header.length - bytesRead);
                    if (count < 0) {
                        throw new IOException("Reached end of file before reading full header");
                    }
                    bytesRead += count;
                }

                // Extract game title
                String extractedTitle = new String(header, GAME_TITLE_OFFSET, GAME_TITLE_LENGTH).trim();
                // Extract game code
                String gameCode = new String(header, GAME_CODE_OFFSET, GAME_CODE_LENGTH);

                // Extract region (last character of the game code)
                char regionChar = gameCode.charAt(3);
                String region = getRegionFromCode(regionChar);

                // Ask user for the title of the game
                System.out.println(Colores.BLUE + "\nGame Title: \"" + extractedTitle + "\"" + Colores.RESET);
                System.out.println(Colores.BLUE + "Game Code: \"" + gameCode + "\"" + Colores.RESET);
                System.out.println(Colores.BLUE + "Game Region: \"" + region + "\"" + Colores.RESET);
                System.out.print("Enter a new title or press Enter to keep the current title: ");
                String title = scanner.nextLine().trim();
                if (title.isEmpty()) {
                    title = extractedTitle;
                }

                // Ask user for the year of the game
                System.out.print("Enter the release year for the game \"" + title + "\": ");
                String year = scanner.nextLine().trim();

                // Construct the new file name
                String newFileName = title + " (" + year + ") [" + gameCode + "] [" + region + "].nds";
                File newFile = new File(directory, newFileName);

                // Rename the file
                fis.close();
                if (ndsFile.renameTo(newFile)) {
                    System.out.println("Renamed: " + ndsFile.getName() + " -> " + newFileName);
                } else {
                    System.out.println("Failed to rename: " + ndsFile.getName());
                }

            } catch (IOException e) {
                System.err.println("Error reading the file: " + ndsFile.getName() + " - " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static String getRegionFromCode(char regionChar) {
        switch (regionChar) {
            case 'E':
                return "USA";
            case 'P':
                return "EUR";
            case 'V':
                return "EUR";
            case 'J':
                return "JPN";
            default:
                return "REGION-"+regionChar;
        }
    }
}