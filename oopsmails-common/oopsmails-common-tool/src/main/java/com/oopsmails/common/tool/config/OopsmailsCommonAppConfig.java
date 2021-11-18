package com.oopsmails.common.tool.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.ZoneId;

@SpringBootConfiguration
public class OopsmailsCommonAppConfig {

    @Bean
    public ObjectMapper objectMapperForLoggingCommonTool() {
        SimpleModule dateSerializerModule = new SimpleModule();

        ObjectMapper result = new ObjectMapper();
        result.registerModule(dateSerializerModule);
        result.registerModule(new JavaTimeModule());
        result.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        result.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        result.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        result.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, false);
        result.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        result.configure(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL, true);

        return result;
    }

    @Bean
    public Clock appClockCommonTool() {
        Clock result1 = Clock.system(ZoneId.of("Canada/Eastern"));
        Clock result = Clock.systemDefaultZone();
        return result;
    }
}
