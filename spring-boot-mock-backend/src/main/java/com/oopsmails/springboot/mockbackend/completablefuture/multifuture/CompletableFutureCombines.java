package com.oopsmails.springboot.mockbackend.completablefuture.multifuture;

import com.oopsmails.springboot.mockbackend.completablefuture.CompletableFutureUtil;
import com.oopsmails.springboot.mockbackend.service.GeneralStaticService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

@Slf4j
public class CompletableFutureCombines {

    public static void main(String[] args) {
        CompletableFutureCombines completableFutureCombines = new CompletableFutureCombines();
        ExecutorService executorService = CompletableFutureUtil.executorService;

        try {
            Double weightInKg = 63.0;
            Double heightInCm = 168.0;
            Double result = completableFutureCombines.runGetTitleCombine(executorService, weightInKg, heightInCm);

            log.info("main() done .... back in main thread, result = [{}]", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    public Double runGetTitleCombine(Executor executor, Double weightInKg, Double heightInCm) throws ExecutionException, InterruptedException {
        Double result = getCombinedBmiFuture(executor, weightInKg, heightInCm).get();
        log.info("runGetTitleCombine: [{}]", result);
        return result;
    }

    public static CompletableFuture<Double> getCompletableFutureWeight(Executor executor, Double iDouble) {
        return CompletableFuture.supplyAsync(() -> {
                    Double result = GeneralStaticService.getDoubleWithDelay(iDouble);
                    log.info("getCompletableFutureWeight: [{}]", result);
                    return result;
                }, executor
        );
    }

    public static CompletableFuture<Double> getCompletableFutureHeight(Executor executor, Double iDouble) {
        return CompletableFuture.supplyAsync(() -> {
                    Double result = GeneralStaticService.getDoubleWithDelay(iDouble);
                    log.info("getCompletableFutureHeight: [{}]", result);
                    return result;
                }, executor
        );
    }

    public static CompletableFuture<Double> getCombinedBmiFuture(Executor executor, Double weightInKg, Double heightInCm) {
        return getCompletableFutureWeight(executor, weightInKg)
                .thenCombine(getCompletableFutureHeight(executor, heightInCm),
                        (weightInKgCalc, heightInCmCalc) -> {
                            Double heightInMeter = heightInCmCalc / 100;
                            return weightInKgCalc / (heightInMeter * heightInMeter);
                        });
    }
}
