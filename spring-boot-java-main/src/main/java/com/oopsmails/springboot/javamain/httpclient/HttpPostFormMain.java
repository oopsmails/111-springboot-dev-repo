package com.oopsmails.springboot.javamain.httpclient;

import com.oopsmails.springboot.httpclient.HttpPostForm;

import java.util.HashMap;
import java.util.Map;

public class HttpPostFormMain {
    public static void main(String[] args) {
        try {
            // Headers
            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
            HttpPostForm httpPostForm = new HttpPostForm("http://localhost", "utf-8", headers);
            // Add form field
            httpPostForm.addFormField("username", "用户名");
            httpPostForm.addFormField("password", "test_psw");
            // Result
            String response = httpPostForm.finish();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
