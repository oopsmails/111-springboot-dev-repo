package com.oopsmails.springboot.javamain;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {
    Queue<String> globalQueue = new ConcurrentLinkedQueue<String>();
}
