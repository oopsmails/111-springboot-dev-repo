package com.oopsmails.generaljava;

import com.oopsmails.common.tool.json.JsonUtil;
import com.oopsmails.model.Customer;
import com.oopsmails.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
public class SimplifyTestingService {

    public static <T> void consumerAcceptWithLogging(T data, Consumer<T> consumer) { // passing in together data for consumer with Consumer itself
        String uuid = UUID.randomUUID().toString();
        log.info("consumerAcceptWithLogging, consumer: [{}], data: [{}]",
                consumer,
                JsonUtil.objectToJsonString(data, true));
        consumer.accept(data);
    }

    public static <T> T supplierGetWithLogging(Supplier<T> supplier) {
        log.info("supplierGetWithLogging, supplier: [{}]", supplier);
        return supplier.get();
    }

    public static <T, R> R functionApplyWithLogging(T data, Function<T, R> function) {
        log.info("functionApplyWithLogging, function: [{}], before data: [{}]",
                function,
                JsonUtil.objectToJsonString(data, true));

        R result = function.apply(data);

        log.info("functionApplyWithLogging, do processing after function.apply() for:  result: [{}]",
                JsonUtil.objectToJsonString(result, true));
        return result;
    }

    public static Customer biFunctionApply(BiFunction<String, CustomerDto, Customer> function, String middleName) {
        log.info("biFunctionApply: for doing some GENERIC logics before and after");

        String addingBefore = "InsideAddBeforeMiddle .. ";

        CustomerDto customerDto = new CustomerDto();
        customerDto.setMiddleName(addingBefore + middleName);

        Customer functionResult = function.apply(middleName, customerDto);

        String addingAfter = ": InsideAddAfterFuncApplied, fullName processed! \n";

        functionResult.setFullName(functionResult.getFullName() + addingAfter);

        return functionResult;
    }

    public static List<Customer> getTestCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customer.setId("" + i);
            customer.setAge(i + 20);
            customer.setEmail("customer" + i + "@test.com");
            customer.setFirstName("First" + i);
            customer.setLastName("Last" + i);
            customers.add(customer);
        }
        return customers;
    }
}
