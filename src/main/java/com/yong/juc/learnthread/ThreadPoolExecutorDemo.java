package com.yong.juc.learnthread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(5);
        Executors.newSingleThreadExecutor();
    }
}
