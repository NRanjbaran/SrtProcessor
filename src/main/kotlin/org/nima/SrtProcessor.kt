import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    println("Enter the directory path (e.g., C:\\Users\\YourName\\Documents\\Subtitles):")
    val folderPath = scanner.nextLine()
    scanner.close()

    val folder = File(folderPath)

    if (!folder.isDirectory) {
        println("The provided path is not a folder.")
        return
    }

    val processedFolder = File(folder, "processed")
    if (!processedFolder.exists()) {
        if (!processedFolder.mkdir()) {
            println("Failed to create 'processed' folder.")
            return
        }
    }

    val files = folder.listFiles { _, name -> name.endsWith(".ir.srt") }
    if (files == null || files.isEmpty()) {
        println("No .ir.srt files found in the specified folder.")
        return
    }

    for (file in files) {
        processSrtFile(file, processedFolder)
    }

    println("Processing completed.")
}

fun processSrtFile(file: File, processedFolder: File) {
    val outputFile = File(processedFolder, file.name.replace(".ir.srt", ".processed.srt"))

    try {
        BufferedReader(FileReader(file)).use { reader ->
            BufferedWriter(FileWriter(outputFile)).use { writer ->
                val textBuilder = StringBuilder()

                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    if (line!!.trim().isEmpty() || line!!.matches("\\d+".toRegex()) || line!!.contains("-->")) {
                        // Skip empty lines, lines with only numbers, and lines with time ranges
                        continue
                    }
                    textBuilder.append(line).append(" ")
                }

                // Write the concatenated text to the output file
                writer.write(textBuilder.toString().trim())
            }
        }
    } catch (e: IOException) {
        println("Error processing file: ${file.name}")
        e.printStackTrace()
    }
}
