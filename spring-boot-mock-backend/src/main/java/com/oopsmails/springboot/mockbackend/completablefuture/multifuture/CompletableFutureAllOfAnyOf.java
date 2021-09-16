package com.oopsmails.springboot.mockbackend.completablefuture.multifuture;

import com.oopsmails.springboot.mockbackend.completablefuture.CompletableFutureUtil;
import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import com.oopsmails.springboot.mockbackend.service.GeneralStaticService;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Slf4j
public class CompletableFutureAllOfAnyOf {

    public static void main(String[] args) {
        List<Long> employeeIds = new ArrayList<>();
        employeeIds.add(1L);
        employeeIds.add(2L);
        employeeIds.add(5L);
        employeeIds.add(8L);
        employeeIds.add(7L);

        Instant instantStart = Instant.now();
        List<Employee> resultForRef = GeneralStaticService.getEmployeeByIdsMock(employeeIds);
        Instant instantEnd = Instant.now();
        long duration = Duration.between(instantStart, instantEnd).toMillis();
        log.info("main() done sequentially, execution time = {}, result = [{}]", duration, resultForRef);

        CompletableFutureAllOfAnyOf completableFutureAllOfAnyOf = new CompletableFutureAllOfAnyOf();
        ExecutorService executorService = CompletableFutureUtil.executorService;

        try {
            instantStart = Instant.now();
            List<Employee> result = completableFutureAllOfAnyOf.runGetTitleAllEmployee(executorService, employeeIds);
            instantEnd = Instant.now();
            duration = Duration.between(instantStart, instantEnd).toMillis();
            log.info("main() done .... back in main thread, execution time = {}, result = [{}]", duration, result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    public List<Employee> runGetTitleAllEmployee(ExecutorService executorService, List<Long> employeeIds) throws ExecutionException, InterruptedException {
        List<Employee> result = runGetTitleAllOf(executorService, employeeIds).get();

        return result;
    }

    public CompletableFuture<List<Employee>> runGetTitleAllOf(ExecutorService executorService, List<Long> employeeIds) {
        List<CompletableFuture<Employee>> futureList = new ArrayList<>();
        employeeIds.forEach(
                employeeId -> {
                    CompletableFuture<Employee> result = CompletableFutureCompose.getCompletableFutureEmployee(executorService, employeeId);
                    futureList.add(result);
                }
        );

        return CompletableFutureUtil.allOf(futureList);
    }
}
