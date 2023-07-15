package com.oopsmails.generaljava.filesys;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.List;

/**
 * Deal with hidden folders and hidden files, not including hidden.
 */
public class FileCopyMainV21 {
    public static void main(String[] args) {
        String rootFolderPath = "C:/tmp/";
        String sourceFolderPath = rootFolderPath + "s0";
        String destinationFolderPath = rootFolderPath + "t";

        long start = System.currentTimeMillis();
        List<String> excludedFileNames = List.of("file1.txt", "file2.txt");
        List<String> excludedFolderNames = List.of("target", ".git", ".idea", "node_modules");

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
                } else if (file.isHidden()) {
                    continue;
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