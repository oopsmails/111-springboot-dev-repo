package com.oopsmails.generaljava.filesys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileListingMain {
    public static void main(String[] args) {
        System.out.println("===================================================================================");
        //The source directory
        String directory = "/home/albert/Documents/dev/jsfsimple";

        // Reading only files in the directory
        try {
            List<File> files = Files.list(Paths.get(directory))
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .collect(Collectors.toList());

            files.forEach(System.out::println);

            System.out.println("===================================================================================");

            List<Path> pathList = new ArrayList<>();

            try (Stream<Path> stream = Files.walk(Paths.get(directory))) {
                pathList = stream.map(Path::normalize)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".java"))
                        .collect(Collectors.toList());
            }

            pathList.forEach(System.out::println);

            System.out.println("===================================================================================");
            //Recursively list all files
            List<File> fileList = listFiles(directory);
            fileList.stream()
                    .filter(file -> file.getName().endsWith(".java")).collect(Collectors.toList())
                    .forEach(System.out::println);

            System.out.println("===================================================================================");
            // hidden files
            files = Files.list(Paths.get(directory))
                    .filter(path -> path.toFile().isHidden())
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            files.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<File> listFiles(final String directory) {
        if (directory == null) {
            return Collections.EMPTY_LIST;
        }
        List<File> fileList = new ArrayList<>();
        File[] files = new File(directory).listFiles();
        for (File element : files) {
            if (element.isDirectory()) {
                fileList.addAll(listFiles(element.getPath()));
            } else {
                fileList.add(element);
            }
        }
        return fileList;
    }
}
