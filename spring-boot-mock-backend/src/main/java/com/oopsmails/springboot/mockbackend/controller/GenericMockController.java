package com.oopsmails.springboot.mockbackend.controller;

import com.oopsmails.springboot.mockbackend.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("")
@Slf4j
public class GenericMockController {
    @Value("${generic.redirect.url.exclude}")
    private String genericRedirectUrlExclude;

    @GetMapping("/ping")
    public String pingGet() throws Exception {
        return JsonUtils.readFileAsString("data/ping/ping.get.response.data.json");
    }

    @PostMapping("/ping")
    public String pingPost(@RequestBody String anyThing) throws Exception {
        return JsonUtils.readFileAsString("data/ping/ping.post.response.data.json");
    }

    @GetMapping("")
    public String genericGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String responseFilePath = getResponseFilePath(httpServletRequest);
        return JsonUtils.readFileAsString(responseFilePath);
    }

    @PostMapping("")
    public String genericPost(@RequestBody String anyThing, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String responseFilePath = getResponseFilePath(httpServletRequest);
        return JsonUtils.readFileAsString(responseFilePath);
    }

    private String getResponseFilePath(HttpServletRequest httpServletRequest) {
        log.info("httpServletRequest.getRequestURL(): {}", httpServletRequest.getRequestURL());
        String urlPath = httpServletRequest.getRequestURL().toString();
        urlPath = urlPath.substring(urlPath.indexOf(genericRedirectUrlExclude) + genericRedirectUrlExclude.length());
        log.info("urlPath: {}", urlPath);


        StringBuilder responseMockDataPathSB = new StringBuilder();
        urlPath = !urlPath.contains("?") ? urlPath : urlPath.substring(0, urlPath.indexOf("?"));

        String requestMethod = httpServletRequest.getMethod();

        List<String> splitList = Arrays.asList(urlPath.split("/"));
        if (!splitList.isEmpty()) {
            for (String s : splitList) {
                log.info("responseMockDataPathSB appending: {}", s);
                responseMockDataPathSB.append(s);
                responseMockDataPathSB.append(".");
            }
        }
        responseMockDataPathSB.append(requestMethod.toLowerCase());
        responseMockDataPathSB.append(".");
        responseMockDataPathSB.append("data.json");

        String responseMockDataPath = responseMockDataPathSB.toString();
        if (responseMockDataPath.indexOf(".") == 0) { // only / in path
            responseMockDataPath = responseMockDataPath.substring(1);
        }
        log.info("responseMockDataPathSB: {}", responseMockDataPath);

        StringBuilder resultSB = new StringBuilder("data/");
        resultSB.append(responseMockDataPath);

        log.info("getResponseFilePath() returning: {}", resultSB);
        return resultSB.toString();
    }
}
