package com.oopsmails.springboot.service;

import com.oopsmails.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CustomerService {

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Customer> getCustomerByID(final String id) throws InterruptedException {
        log.info("Filling the customer details for id {} ", id);
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName("Javadev");
        customer.setLastName("Journal");
        customer.setAge(34);
        customer.setEmail("contact-us@javadevjournal");
        // doing an artificial sleep
        Thread.sleep(5000);
        return CompletableFuture.completedFuture(customer);
    }

    @Async("threadPoolTaskExecutor")
    public void updateCustomer(Customer customer) throws Exception {
        log.warn("Running method with thread {} :", Thread.currentThread().getName());
        throw new Exception("Throw exception to check the custom exception handling");
    }

    public Customer getCustomerByEmail(String email) throws InterruptedException {
        log.info("Filling the customer details for email {} ", email);
        Customer customer = new Customer();
        customer.setFirstName("New");
        customer.setLastName("Customer");
        customer.setAge(30);
        customer.setEmail("contact-us@javadevjournal");
        Thread.sleep(20000);
        return customer;
    }
}
