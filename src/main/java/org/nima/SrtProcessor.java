package org.nima;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class SrtProcessor {

    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            System.out.println("Enter the directory path (e.g., C:\\Users\\YourName\\Documents\\Subtitles):");
            String folderPath = scanner.nextLine();

            var folder = Path.of(folderPath);

            if (!Files.isDirectory(folder)) {
                System.out.println("The provided path is not a folder.");
                return;
            }

            var processedFolder = folder.resolve("processed");
            if (Files.notExists(processedFolder)) {
                try {
                    Files.createDirectory(processedFolder);
                } catch (IOException e) {
                    System.out.println("Failed to create 'processed' folder.");
                    e.printStackTrace();
                    return;
                }
            }

            try (var files = Files.list(folder)) {
                files.filter(path -> path.toString().endsWith(".ir.srt"))
                        .forEach(file -> processSrtFile(file, processedFolder));
            } catch (IOException e) {
                System.out.println("Error reading files from the specified folder.");
                e.printStackTrace();
            }

            System.out.println("Processing completed.");
        }
    }

    private static void processSrtFile(Path file, Path processedFolder) {
        var outputFile = processedFolder.resolve(file.getFileName().toString().replace(".ir.srt", ".processed.srt"));

        try (var reader = Files.newBufferedReader(file);
             var writer = Files.newBufferedWriter(outputFile)) {

            var textBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.matches("\\d+") || line.contains("-->")) {
                    // Skip empty lines, lines with only numbers, and lines with time ranges
                    continue;
                }
                textBuilder.append(line).append(" ");
            }

            // Write the concatenated text to the output file
            writer.write(textBuilder.toString().trim());

        } catch (IOException e) {
            System.err.println("Error processing file: " + file.getFileName());
            e.printStackTrace();
        }
    }
}
