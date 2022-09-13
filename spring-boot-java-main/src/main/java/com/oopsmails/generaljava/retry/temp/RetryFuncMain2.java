package com.oopsmails.generaljava.retry.temp;

import com.oopsmails.model.RetryableException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class RetryFuncMain2 {
    static String directoryPath = "/temporary/blogs";
    static RetryLogicWapper retryLogicWapper = new RetryLogicWapper(5, 2000);

    public static void main(String[] args) throws RetryableException {
        retryRun();
    }

    static void retryRun() throws RetryableException {
        try {
            Files.createDirectories(Paths.get(directoryPath));

        } catch (Exception e) {
            retryLogicWapper.retryImpl(() -> {
                try {
                    retryRun();
                } finally {

                }
                return true;
            });
        }
    }
}
