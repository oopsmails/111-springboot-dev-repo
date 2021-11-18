package com.oopsmails.common.tool.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class JsonCommonService {

    @Autowired
    @Qualifier("objectMapperForLoggingCommonTool")
    private ObjectMapper objectMapperJsonCommonService;

    public void getJsonString(Object obj, boolean beautify) {
        String prettyJsonStr = "";
        try {
            prettyJsonStr = beautify ?
                    objectMapperJsonCommonService.writerWithDefaultPrettyPrinter().writeValueAsString(obj) :
                    objectMapperJsonCommonService.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("logging object error, obj: [{}]", obj);
            log.warn("logging object error, exception will be ignored:[{}] ", e.getMessage());
        }
        log.trace("- logging object: [{}]", prettyJsonStr);
    }

    public <T> T jsonFileToObject(String fileNameWithPath, Class<T> type) {
        T target = null;
        try {
            File jsonFile = new File(fileNameWithPath);
            target = objectMapperJsonCommonService.readValue(jsonFile, type);
        } catch (Exception e) {
            log.warn("logging object error, obj: [{}]", e.getMessage());
        }

        return target;
    }

    public <T> T jsonFileToObject(String fileNameWithPath, TypeReference<T> type) {
        T target = null;
        try {
            File jsonFile = new File(fileNameWithPath);
            target = objectMapperJsonCommonService.readValue(jsonFile, type);
        } catch (Exception e) {
            log.warn("logging object error, obj: [{}]", e.getMessage());
        }

        return target;
    }

    public <T> T jsonToObject(String json, Class<T> type) {
        T target = null;
        try {
            target = objectMapperJsonCommonService.readValue(json, type);
        } catch (Exception e) {
            log.warn("logging object error, obj: [{}]", e.getMessage());
        }

        return target;
    }

    public <T> T jsonToObject(String json, TypeReference<T> type) {
        T target = null;
        try {
            target = objectMapperJsonCommonService.readValue(json, type);
        } catch (Exception e) {
            log.warn("logging object error, obj: [{}]", e.getMessage());
        }
        return target;
    }

    public String readFileAsString(String pathLocation) throws Exception {
        Path dataPath = Paths.get(ClassLoader.getSystemResource(pathLocation).toURI());
        return new String(Files.readAllBytes(dataPath));
    }
}
