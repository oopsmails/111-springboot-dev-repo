package com.oopsmails.generaljava.filesys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

/**
 * NOT dealing with hidden files, paths as args, java 8
 * <p>
 * Enter the source folder path: C:/tmp/source
 * Enter the destination folder path: C:/tmp/target
 */
public class FileCopyMainV13 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the source folder path: ");
        String sourceFolderPath = scanner.nextLine();

        System.out.print("Enter the destination folder path: ");
        String destinationFolderPath = scanner.nextLine();

        scanner.close();

        try {
            // Create a File object for the source and destination folders
            File sourceFolder = new File(sourceFolderPath);
            File destinationFolder = new File(destinationFolderPath);

            // Copy all files and subfolders recursively from source to destination
            copyFolder(sourceFolder, destinationFolder);

            System.out.println("Files and subfolders copied successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while copying files and subfolders: " + e.getMessage());
        }
    }

    private static void copyFolder(File source, File destination) throws IOException {
        // Create the destination folder if it doesn't exist
        if (!destination.exists()) {
            destination.mkdirs();
        }

        // Iterate over all items in the source folder
        File[] files = source.listFiles();
        if (files != null) {
            for (File file : files) {
                File destinationFile = new File(destination, file.getName());

                // Recursively copy subfolders and files
                if (file.isDirectory()) {
                    copyFolder(file, destinationFile);
                } else {
                    Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}
