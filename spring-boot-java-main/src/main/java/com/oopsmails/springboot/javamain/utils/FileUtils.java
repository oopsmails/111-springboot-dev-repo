package com.oopsmails.springboot.javamain.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.net.URL;

@Slf4j
public class FileUtils {
    public static final String FILE_NAME_VALIDATION_REGEX = "^[a-zA-Z0-9.:/\\[\\]()_\\-]{1,255}$";

    private FileUtils() {

    }

    public static boolean isValidFileName(String fileName) {
        if (!fileName.matches(FILE_NAME_VALIDATION_REGEX)) {
            log.info("\nInvalid char(s) in file name ====> {}\n", fileName);
            return false;
        }

        if (fileName.contains("..")) {
            log.info("\n.. not allowed in file name ====> {}\n", fileName);
            return false;
        }

        return true;
    }

    public static InputStream getInputStreamFromFile(String fileName) {
        return getInputStreamFromFileInFolder(fileName, null);
    }

    public static InputStream getInputStreamFromFileInFolder(String fileName, String dirName) {
        if (StringUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("File name is required");
        }

        if (StringUtils.isBlank(dirName)) {
            if (isValidFileName(fileName)) {
                return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            }
            return null;
        }

        String fullName = dirName + fileName;
        if (!fullName.startsWith("file:")) {
            fullName = "file:///" + fullName;
        }

        if (isValidFileName(fullName)) {
            try {
                return new URL(fullName).openStream();
            } catch (Exception e) {
                e.printStackTrace();
                log.info("File {} cannot be loaded, error: \n{}", fullName, e.toString());
            }
        }

        return null;
    }
}
