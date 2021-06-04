package com.oopsmails.springboot.javamain.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ResourceLoadingUtils {
    public static final String APP_CONFIG_LOCATION = "app.config.location";

    private static String configDir;

    static {
        init();
    }

    private ResourceLoadingUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static void init() {
        String configFolder = System.getProperty(APP_CONFIG_LOCATION);
        log.info("\nResourceLoadingUtils, loading from configFolder ==> {}\n", configFolder);
        if (StringUtils.isNotBlank(configFolder)) {
            if (!StringUtils.endsWith(configFolder, "/") && !StringUtils.endsWith(configFolder, "\\")) {
                configFolder = configFolder + File.separator;
                log.info("\nResourceLoadingUtils, loading from configFolder ====> {}\n", configFolder);
            }
            configDir = configFolder;
        }

        log.info("\nResourceLoadingUtils, loading app configure {} ====> {}\n", APP_CONFIG_LOCATION, configDir);
    }

    public static <T> T loadYaml(String fileName, Class<T> clz) {
        if (StringUtils.isEmpty(fileName)) {
            log.warn("fileName is required.");
            return null;
        }

        InputStream inputStream = null;
        try {
            final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            inputStream = FileUtils.getInputStreamFromFileInFolder(fileName, configDir);
            T result = objectMapper.readValue(inputStream, clz);
            log.info("Loaded yaml file {}, clz = ", fileName, clz.toString());
            return result;
        } catch (Exception e) {
            log.info("Failed to close input stream, error: {}", e.toString());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    log.warn("Failed to close input stream, error: {}", e.toString());
                }
            }
        }
        return null;
    }

    public static <T> T resourceToObject(Resource resource, Class<T> clz, String... params) {
        File file = null;
        try {
            file = resource.getFile();
            String content = new String(Files.readAllBytes(file.toPath()));
            return JsonUtils.jsonToObject(addParams(content, params), clz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getResourceContent(Resource resource) {
        File file = null;
        try {
            file = resource.getFile();
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static <T> T pathToObject(Path path, Class<T> clz, String... params) {
        try {
            String content = new String(Files.readAllBytes(path));
            return JsonUtils.jsonToObject(addParams(content, params), clz);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T pathLocationToObject(String pathLocation, Class<T> clz, String... params) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(pathLocation).toURI());
            return pathToObject(path, clz, params);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String addParams(String content, String... params) {
        if (params.length > 0) {
            String paramsContent = content;
            Pattern pattern = Pattern.compile("\\{\\{[a-zA-Z0-9]*\\}\\}}");
            Matcher matcher = pattern.matcher(content);

            int i = 0;
            while (matcher.find() && i < params.length) {
                paramsContent = paramsContent.replace(matcher.group(0), params[i]);
                ++i;
            }

            return paramsContent;
        }

        return content;
    }
}
