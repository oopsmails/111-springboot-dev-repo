package com.oopsmails.springboot.mockbackend.completablefuture.multifuture;

import com.oopsmails.springboot.mockbackend.completablefuture.CompletableFutureUtil;
import com.oopsmails.springboot.mockbackend.employee.model.Employee;
import com.oopsmails.springboot.mockbackend.employee.model.EmployeeTitle;
import com.oopsmails.springboot.mockbackend.service.GeneralStaticService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Slf4j
public class CompletableFutureCompose {

    public static void main(String[] args) {
        CompletableFutureCompose completableFutureCombines = new CompletableFutureCompose();
        ExecutorService executorService = CompletableFutureUtil.executorService;

        try {
            Long employeeId = 1L;
            String result = completableFutureCombines.runGetTitleCompose(executorService, employeeId);

            log.info("main() done .... back in main thread, result = [{}]", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    public String runGetTitleCompose(Executor executor, Long employeeId) throws ExecutionException, InterruptedException {
        String result = getComposedCompletableFutureEmployeeTitle(executor, employeeId).get();
        log.info("runGetTitleCompose: [{}]", result);
        return result;
    }

    public static CompletableFuture<Employee> getCompletableFutureEmployee(Executor executor, Long employeeId) {
        return CompletableFuture.supplyAsync(() -> {
                    Employee result = GeneralStaticService.getEmployeeByIdMock(employeeId);
                    log.info("getCompletableFutureEmployee: [{}]", result);
                    return result;
                }, executor
        );
    }

    public static CompletableFuture<String> getCompletableFutureEmployeeTitle(Executor executor, Employee employee) {
        return CompletableFuture.supplyAsync(() -> {
                    EmployeeTitle employeeTitle = GeneralStaticService.getEmployeeTitleByPosition(employee.getPosition());
                    log.info("getCompletableFutureEmployeeTitle: [{}]", employeeTitle);
                    return employeeTitle.getTitle();
                }, executor
        );
    }

    public static CompletableFuture<String> getComposedCompletableFutureEmployeeTitle(Executor executor, Long employeeId) throws ExecutionException, InterruptedException {
        return getCompletableFutureEmployee(executor, employeeId)
                .thenCompose(employee -> getCompletableFutureEmployeeTitle(executor, employee));
    }
}
