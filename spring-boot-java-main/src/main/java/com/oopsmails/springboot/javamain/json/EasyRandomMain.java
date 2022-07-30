package com.oopsmails.springboot.javamain.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.oopsmails.springboot.javamain.SpringBootJavaMainApplication;
import com.oopsmails.springboot.javamain.model.Product;
import com.oopsmails.springboot.javamain.model.Role;
import com.oopsmails.springboot.javamain.model.TodoDTO;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.TypePredicates;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.jeasy.random.FieldPredicates.inClass;

/**
 * https://github.com/j-easy/easy-random
 * <p>
 * EasyRandomParameters parameters = new EasyRandomParameters()
 * .seed(123L)
 * .objectPoolSize(100)
 * .randomizationDepth(3)
 * .charset(forName("UTF-8"))
 * .timeRange(nine, five)
 * .dateRange(today, tomorrow)
 * .stringLengthRange(5, 50)
 * .collectionSizeRange(1, 10)
 * .scanClasspathForConcreteTypes(true)
 * .overrideDefaultInitialization(false)
 * .ignoreRandomizationErrors(true);
 * <p>
 * EasyRandomParameters parameters = new EasyRandomParameters()
 * .randomize(String.class, () -> "foo")
 * .excludeField(named("age").and(ofType(Integer.class)).and(inClass(Person.class)));
 */

public class EasyRandomMain {
    public static final List<String> HTTP_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH");

    public static void main(String[] args) {
        try {
            EasyRandomParameters parameters = new EasyRandomParameters()
                    .seed(123L)
                    .objectPoolSize(100)
                    .randomizationDepth(3)
                    .charset(StandardCharsets.UTF_8)
                    .stringLengthRange(5, 6)
                    .collectionSizeRange(1, 1)
                    .scanClasspathForConcreteTypes(true)
                    .overrideDefaultInitialization(false)
                    .ignoreRandomizationErrors(true);

            Random random = new Random();
            parameters.randomize(BigDecimal.class, () -> new BigDecimal(BigInteger.valueOf(random.nextInt(100001)), 2));
            parameters.randomize(int.class, () -> random.nextInt(1200 - 55) + 55);
            parameters.randomize(Integer.class, () -> random.nextInt(1200 - 55) + 55);
            parameters.randomize(long.class, () -> 10L + (long) (Math.random() * (1888L - 10L)));
            parameters.randomize(Long.class, () -> 10L + (long) (Math.random() * (1888L - 10L)));

            parameters.randomize(FieldPredicates.named("url").and(inClass(SpringBootJavaMainApplication.class)), () -> "http://url.ApiClientSpecification");
            parameters.randomize(FieldPredicates.named("httpMethod"), () -> HTTP_METHODS.get(random.nextInt(HTTP_METHODS.size())));

            parameters.excludeType(TypePredicates.ofType(SpringBootJavaMainApplication.class)); // no default constructor

            EasyRandom easyRandom = new EasyRandom(parameters);
            Product nextObject = easyRandom.nextObject(Product.class);
            System.out.println(nextObject);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new SimpleModule());
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            ObjectWriter prettyPrinter = objectMapper.writerWithDefaultPrettyPrinter();

            String json = prettyPrinter.writeValueAsString(nextObject);
            System.out.println(json);

            objectMapper.writeValue(Paths.get("nextObject.json").toFile(), nextObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

