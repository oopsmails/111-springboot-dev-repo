package com.oopsmails.springboot.javamain.worker;

import com.oopsmails.model.MimicDto;
import com.oopsmails.worker.WorkerMimicDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WorkerMain {
    public static void main(String[] args) throws InterruptedException {
        new WorkerMain().letsTest();
    }

    public static void performJobAsyncCorrect(MimicDto d) {
        WorkerMimicDto w = new WorkerMimicDto(d);

        CompletableFuture<MimicDto> future = CompletableFuture.supplyAsync(w);
        future.thenAccept(w);

        d.setName("A: " + d.getName());
    }

    public static void performJobAsyncWrong(MimicDto d) {
        CompletableFuture<MimicDto> future = CompletableFuture.supplyAsync(new Supplier<MimicDto>() {
            @Override
            public MimicDto get() {
                try {
                    Thread.sleep(10000);
                    d.setStatus("Finished");
                    System.out.println("In " + Thread.currentThread().getName() + ": " + d);
                    return d;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        });

        future.thenAccept(new Consumer<MimicDto>() {
            @Override
            public void accept(MimicDto t) {
                System.out.println(Thread.currentThread().getName() + " " + t.getStatus());
            }
        });
    }

    public void letsTest() throws InterruptedException {
        MimicDto dto1 = new MimicDto();
        dto1.setId("Id-1");
        dto1.setName("Name 1");
        dto1.setIndex(1);

        MimicDto dto2 = new MimicDto();
        dto2.setId("Id-2");
        dto2.setName("Name 2");
        dto2.setIndex(2);

        MimicDto dto3 = new MimicDto();
        dto3.setId("Id-3");
        dto3.setName("Name 3");
        dto3.setIndex(3);

        List<MimicDto> list = new ArrayList<>();
        list.add(dto1);
        list.add(dto2);
        list.add(dto3);

        for (MimicDto d : list) {
            System.out.println("performJobAsyncCorrect Before - " + d.toString());
            performJobAsyncCorrect(d);
            System.out.println("performJobAsyncCorrect After - " + d.toString());
        }


        System.out.println("=====================================================");
        Thread.sleep(2000000);

        MimicDto dto4 = new MimicDto();
        dto4.setId("Id-4");
        dto4.setName("Name 4");
        dto4.setIndex(4);

        MimicDto dto5 = new MimicDto();
        dto5.setId("Id-5");
        dto5.setName("Name 5");
        dto5.setIndex(5);

        MimicDto dto6 = new MimicDto();
        dto6.setId("Id-6");
        dto6.setName("Name 6");
        dto6.setIndex(6);

        List<MimicDto> _list = new ArrayList<>();
        _list.add(dto4);
        _list.add(dto5);
        _list.add(dto6);

        for (MimicDto d : list) {
            System.out.println("performJobAsyncWrong Before - " + d.toString());
            performJobAsyncWrong(d);
            System.out.println("performJobAsyncWrong After - " + d.toString());
        }
    }
}

