package com.oopsmails.generaljava.filesys;

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
 * Deal with hidden folders and hidden files, including!!!
 */
public class FileCopyMainV20 {
    public static void main(String[] args) {
        String sourceFolderPath = "path/to/source/folder";
        String destinationFolderPath = "path/to/destination/folder";

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
    }

    private static void copyFolder(Path source, Path destination, List<String> excludedFileNames,
                                   List<String> excludedFolderNames) throws IOException {
        // Create the destination folder if it doesn't exist
        if (!Files.exists(destination)) {
            Files.createDirectories(destination);
        }

        // Copy all files and subfolders recursively from source to destination
        Files.walkFileTree(source, EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        String folderName = dir.getFileName().toString();

                        // Check if the folder is excluded
                        if (excludedFolderNames.contains(folderName)) {
                            return FileVisitResult.SKIP_SUBTREE; // Skip excluded folder
                        }

                        Path targetPath = destination.resolve(source.relativize(dir));
                        Files.copy(dir, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        String fileName = file.getFileName().toString();

                        // Check if the file is excluded
                        if (excludedFileNames.contains(fileName)) {
                            return FileVisitResult.CONTINUE; // Skip excluded file
                        }

                        Path targetPath = destination.resolve(source.relativize(file));
                        Files.copy(file, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        return FileVisitResult.CONTINUE;
                    }
                });
    }
}
