package com.oopsmails.generaljava.filesys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Deal with hidden folders and hidden files, not including hidden. java 8
 */
public class FileCopyMainV22 {
    public static void main(String[] args) {
        String rootFolderPath = "C:/tmp/";
        String sourceFolderPath = rootFolderPath + "s0";
        String destinationFolderPath = rootFolderPath + "t";

        long start = System.currentTimeMillis();
        List<String> excludedFileNames = new ArrayList<>();
        excludedFileNames.add("aaa.bbb");

        List<String> excludedFolderNames = new ArrayList<>();
        excludedFolderNames.add("target");
        excludedFolderNames.add(".git");
        excludedFolderNames.add(".idea");
        excludedFolderNames.add("node_modules");

        try {
            // Create a Path object for the source and destination folders
            File sourceFolder = new File(sourceFolderPath);
            File destinationFolder = new File(destinationFolderPath);

            // Copy all files and subfolders recursively from source to destination
            copyFolder(sourceFolder, destinationFolder, excludedFileNames, excludedFolderNames);

            System.out.println("Files and subfolders copied successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while copying files and subfolders: " + e.getMessage());
        }

        long end = System.currentTimeMillis();
        System.out.println("Time:" + (end - start) + "ms");
    }

    private static void copyFolder(File source, File destination, List<String> excludedFileNames,
                                   List<String> excludedFolderNames) throws IOException {
        // Create the destination folder if it doesn't exist
        if (!destination.exists()) {
            destination.mkdirs();
        }

        // Iterate over all items in the source folder
        File[] files = source.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                File destinationFile = new File(destination, fileName);

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
                    copyFolder(file, destinationFile, excludedFileNames, excludedFolderNames);
                } else {
                    Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}