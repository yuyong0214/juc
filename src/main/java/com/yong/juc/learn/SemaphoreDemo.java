package com.yong.juc.learn;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        //模拟3个停车位
        Semaphore semaphore = new Semaphore(3);
        //模拟6部汽车
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    // 抢到资源
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 抢到车位");
                    TimeUnit.SECONDS.sleep(4);
                    System.out.println(Thread.currentThread().getName() + "\t 停4秒离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    // 释放资源
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
