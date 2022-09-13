package com.oopsmails.generaljava.functionobj;

import com.oopsmails.generaljava.SimplifyTestingService;
import com.oopsmails.model.Customer;
import com.oopsmails.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class FunctionParameterTest {

    @Test
    void testConsumerAsParam() {
        List<String> names = Arrays.asList("John", "Smith", "Samueal", "Catley", "Sie");
        SimplifyTestingService.consumerAcceptWithLogging(
                names,
                list -> list.stream().filter(s -> s.startsWith("S")).forEach(System.out::println));

        assertNotNull(names, "Generally should not be null.");
    }

    @Test
    void testSupplierAsParam() {
        List<String> names = SimplifyTestingService.supplierGetWithLogging(
                () -> Arrays.asList("John", "Smith", "Samueal", "Catley", "Sie")
        );

        assertNotNull(names, "Generally should not be null.");
    }

    @Test
    void testFunctionApply() {
        List<String> names = Arrays.asList("John", "Smith", "Samueal", "Catley", "Sie");
        String result = SimplifyTestingService.functionApplyWithLogging(
                names,
                (List<String> list) -> {
                    return list.stream()
                            .filter(s -> s.startsWith("S"))
                            .reduce("", (s1, s2) -> s1 + "-" + s2);
                }
        );

        assertNotNull(result, "Generally should not be null.");
    }

    @Test
    void testBiFunctionApply() {
//        Customer result = SimplifyTestingService.getTestCustomers().get(1);
//        Customer functionAppliedCustomer = SimplifyTestingService.biFunctionApply(
//                (String middle, CustomerDto cDto) -> {
////                    cDto.setFirstName(result.getFirstName());
////                    cDto.setLastName(result.getLastName());
//                    cDto.setMiddleName(middle);
//                    Customer customer = new Customer();
//                    customer.setFullName(customer.calculateFullName(cDto));
//                    return customer;
//                }, 1, "passedInMiddle"
//        );
//        result.setFullName(functionAppliedCustomer.getFullName());
//        log.info("customer's full name: [{}]", result.getFullName());

        String result = "Outside: before ... \n";

        Customer customer = SimplifyTestingService.getTestCustomers().get(1);

        Customer functionAppliedCustomer = SimplifyTestingService.biFunctionApply(
                (String middle, CustomerDto cDto) -> {
                    String growing = customer.calculateFullName(cDto);
                    customer.setFullName(growing);
                    return customer;
                }, "middleName"
        );

        log.info("customer's full name: [{}]", functionAppliedCustomer.getFullName());

        result = result + functionAppliedCustomer.getFullName() + "Outside: after ....\n";
        log.info("result recording process: [{}]", result);
        assertNotNull(result, "Generally should not be null.");
    }

}
