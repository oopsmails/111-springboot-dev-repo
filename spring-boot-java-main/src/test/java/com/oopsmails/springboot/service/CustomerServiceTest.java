package com.oopsmails.springboot.service;

import com.oopsmails.model.Customer;
import com.oopsmails.springboot.javamain.SpringBootJavaGenericTestBase;
import com.oopsmails.springboot.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.oopsmails.consumer.ThrowingConsumer.throwingConsumerWrapper;

public class CustomerServiceTest extends SpringBootJavaGenericTestBase {
    @Autowired
    private CustomerService customerService;

    @Test
    public void testGetCustomerById() throws Exception {
        System.out.println("testGetCustomerById .....................");
        List<String> taskList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            taskList.add("id-" + i);
        }

//        for (String task : taskList) {
//            CompletableFuture<Customer> customer = customerService.getCustomerByID(task);
//            System.out.printf("customer = [%s]\n", customer.get());
//        }

        taskList.forEach(throwingConsumerWrapper(task -> {
            CompletableFuture<Customer> customer = customerService.getCustomerByID(task);
            System.out.printf("customer = [%s]\n", customer.get());
        }));

    }


}
