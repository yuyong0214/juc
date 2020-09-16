package com.yong.juc.learnthread;

import java.util.concurrent.*;

public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        // 适用:执行很多短期异步的小程序或者负载较轻的服务器
        Executors.newCachedThreadPool();
        // 执行一个长期的任务,性能好很多
        Executors.newFixedThreadPool(5);
        // 一个任务一个线程执行的任务场景
        Executors.newSingleThreadExecutor();
        // java8新增,使用目前机器上可以的处理器作为他的并行级别
        Executors.newWorkStealingPool(2);

        Executors.newScheduledThreadPool(3);
        new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100));
    }
}
