package com.oopsmails.generaljava.functionobj;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SupplierConsumerTest {

    /**
     * A Supplier solves the problem of when you need to be able to tell a library how to create an object as required
     * and a Consumer solves the problem of telling a library about an action you want to perform for each object.
     *
     * Ref: https://stackoverflow.com/questions/38588060/why-do-we-need-a-consumer-and-supplier-functions-in-java-8
     *
     * @throws Exception
     */
    @Test
    void testSupplierConsumerConcepts() {
        Supplier<UUID> uuidSupplier = UUID::randomUUID;
        Consumer<UUID> uuidConsumer = System.out::println;
        Stream.generate(uuidSupplier)
                .limit(100)
                .forEach(uuidConsumer);
        assertNotNull(uuidSupplier, "Generally should not be null.");
    }
}
