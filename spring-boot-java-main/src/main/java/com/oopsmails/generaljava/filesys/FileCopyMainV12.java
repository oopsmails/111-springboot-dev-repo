package com.oopsmails.generaljava.filesys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Scanner;

/**
 * NOT dealing with hidden files, paths as args
 *
 * Enter the source folder path: C:/tmp/source
 * Enter the destination folder path: C:/tmp/target
 */
public class FileCopyMainV12 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the source folder path: ");
        String sourceFolderPath = scanner.nextLine();

        System.out.print("Enter the destination folder path: ");
        String destinationFolderPath = scanner.nextLine();

        scanner.close();

        long start = System.currentTimeMillis();
        List<String> excludedFileNames = List.of("file1.txt", "file2.txt");
        List<String> excludedFolderNames = List.of("target", ".git", ".idea");

        try {
            // Create a Path object for the source and destination folders
            Path sourcePath = Path.of(sourceFolderPath);
            Path destinationPath = Path.of(destinationFolderPath);

            // Copy all files and subfolders recursively from source to destination
            copyFolder(sourcePath, destinationPath, excludedFileNames, excludedFolderNames);

            System.out.println("Files and subfolders copied successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while copying files and subfolders: " + e.getMessage());
        }

        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - start) + "ms");
    }

    private static void copyFolder(Path source, Path destination, List<String> excludedFileNames,
                                   List<String> excludedFolderNames) throws IOException {
        // Create the destination folder if it doesn't exist
        if (!Files.exists(destination)) {
            Files.createDirectories(destination);
        }

        // Iterate over all items in the source folder
        File[] files = source.toFile().listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                Path sourcePath = file.toPath();
                Path destinationPath = destination.resolve(fileName);

                // Check if the file or folder is excluded
                if (file.isDirectory() && excludedFolderNames.contains(fileName)) {
                    continue; // Skip excluded folder
                } else if (!file.isDirectory() && excludedFileNames.contains(fileName)) {
                    continue; // Skip excluded file
                }

                // Recursively copy subfolders and files
                if (file.isDirectory()) {
                    copyFolder(sourcePath, destinationPath, excludedFileNames, excludedFolderNames);
                } else {
                    Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
