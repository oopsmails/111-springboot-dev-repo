package com.oopsmails.springboot.playaroud.jpa.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SpringBootPlayAroundJpaApplicationConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
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

}
