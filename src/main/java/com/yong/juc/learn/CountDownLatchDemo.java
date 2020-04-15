package com.yong.juc.learn;

import java.util.concurrent.CountDownLatch;

/**
 * 有六个同学上自习最后班长锁门
 * CountDownLatch
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 离开了教室");
                // 每执行完成一个线程需要把计数减一，直到计数为0相当于所有的线程都已经执行完成
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        // 首先主线程需要等待其他线程处理完后再执行（所以需要在次等待，知道CountDownLatch的计数归零后再执行放行）
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t锁门");
    }


}
