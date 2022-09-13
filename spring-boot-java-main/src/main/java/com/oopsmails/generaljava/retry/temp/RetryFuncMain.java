package com.oopsmails.generaljava.retry.temp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RetryFuncMain {
    public static void main(String[] args) throws IOException {
        dirCreation();
    }

    static void dirCreation() throws IOException {
        String directoryPath = "/temporary/blogs/";
        try {
            Files.createDirectories(Paths.get(directoryPath));
        } catch (Exception e) {
            int retry = 5;
            while (!Files.exists(Paths.get(directoryPath))
                    && retry != 0) {
                retry--;
                Files.createDirectories(Paths.get(directoryPath));
            }
            if (!Files.exists(Paths.get(directoryPath))) {
                throw new IOException();
            }
        }
    }

}
