package com.oopsmails.generaljava;

import com.oopsmails.common.tool.json.JsonUtil;
import com.oopsmails.model.Customer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class SimpleMain {
    public static void main(String[] args) {
        List<String> positionList = Arrays.asList("Analyst", "Manager", "Developer", "QE");

        for (int i = 0; i < 100; i++) {
            long orgId = i % 2 + 1;
            long depId = i % 4 + 1;
            int randomIndex= i % 4;
            System.out.printf("orgId = %d\n", orgId);
            System.out.printf("depId = %d\n", depId);
            System.out.printf("randomIndex = %s, position = %s\n", randomIndex, positionList.get(randomIndex));
        }

        String jsonFileName = getFullAbsolutePath(SimpleMain.class, "customerMock.json");
        String jsonString = getJsonStringFromFile(jsonFileName);
        Customer customer = JsonUtil.jsonToObject(jsonString, Customer.class);
        System.out.printf("customer = %s\n", customer);
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
//            jsonString = new String(Files.readAllBytes(Path.get(fileName)), StandardCharsets.UTF_8); // java 8
            jsonString = new String(Files.readAllBytes(Path.of(jsonFileName)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}
