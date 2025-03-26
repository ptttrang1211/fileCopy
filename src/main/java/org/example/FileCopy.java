package org.example;
import java.io.*;
public class FileCopy {
    // method to copy file
    public void copyFile(String readFile, String writeFile) {
        File read = new File(readFile);
        File write = new File(writeFile);
        // check file
        if (!read.exists()) {
            System.out.println("Error: not file ");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(read));
             BufferedWriter writer = new BufferedWriter(new FileWriter(write))) {
            // read, write
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("file copy success.");
        } catch (IOException e) {
            System.out.println("error while coping file : " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        FileCopy fileCopy = new FileCopy();
        // example usage
        fileCopy.copyFile("src/main/java/org/example/read.txt", "src/main/java/org/example/write.txt");
    }
}

