package com.oopsmails.generaljava;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println("===================================================================================");


        String input = "1234567890";
        String pattern = "^[0-9]+$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        if (matcher.matches()) {
            System.out.println("Input contains digits only");
        } else {
            System.out.println("Input contains non-digit characters");
        }
    }
}
