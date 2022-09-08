package com.oopsmails.generaljava.filesys;

import com.oopsmails.common.tool.json.JsonUtil;
import com.oopsmails.model.Customer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FilePathMain {
    public static void main(String[] args) {
        System.out.println("===================================================================================");

        String jsonFileName = getFullAbsolutePath(FilePathMain.class, "customerMock.json");
        String jsonString = getJsonStringFromFile(jsonFileName);
        Customer customer = JsonUtil.jsonToObject(jsonString, Customer.class);
        System.out.printf("customer = %s\n", customer);
        System.out.println("===================================================================================");

        printCurrentWorkingDirectory1();
        printCurrentWorkingDirectory2();
        printCurrentWorkingDirectory3();
        printCurrentWorkingDirectory4();

        System.out.println("===================================================================================");
        FilePathMain simpleMain = new FilePathMain();
        String appProp = simpleMain.readFromInputStream(simpleMain.getFileFromResourceAsStream("abc.properties"));
        System.out.printf("appProp = %s\n", appProp);

    }

    public static  <T> String getFullAbsolutePath(Class<T> clazz, String jsonFileName) {
        final File file = new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
        String dirName = "/com/oopsmails/generaljava/";
        String fileName = file.getAbsolutePath() + dirName + jsonFileName;
        return fileName;
    }

    public static String getJsonStringFromFile(String jsonFileName) {
        String jsonString = "";
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(jsonFileName)), StandardCharsets.UTF_8); // java 8
            jsonString = new String(Files.readAllBytes(Path.of(jsonFileName)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    // System Property
    private static void printCurrentWorkingDirectory1() {
        String userDirectory = System.getProperty("user.dir");
        System.out.println(userDirectory);
    }

    // Path, Java 7
    private static void printCurrentWorkingDirectory2() {
        String userDirectory = Paths.get("")
                .toAbsolutePath()
                .toString();
        System.out.println(userDirectory);
    }

    // File("")
    private static void printCurrentWorkingDirectory3() {
        String userDirectory = new File("").getAbsolutePath();
        System.out.println(userDirectory);
    }

    // FileSystems
    private static void printCurrentWorkingDirectory4() {
        String userDirectory = FileSystems.getDefault()
                .getPath("")
                .toAbsolutePath()
                .toString();
        System.out.println(userDirectory);
    }

    // get a file from the resources folder, root of classpath in JAR
    private InputStream getFileFromResourceAsStream(String fileName) {

//        InputStream inputStream = null;
//        try {
//            File file = new File(classLoader.getResource("fileTest.txt").getFile());
//            inputStream = new FileInputStream(file);
//
//            //...
//        }
//        finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private String readFromInputStream(InputStream inputStream) { //  throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultStringBuilder.toString();
    }
}
