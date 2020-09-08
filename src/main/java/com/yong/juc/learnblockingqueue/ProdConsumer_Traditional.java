package com.yong.juc.learnblockingqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class TraditionalData {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            // 判断
            while (number != 0) {
                condition.await();
            }
            // 干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t 生产" + number + "个蛋糕");
            // 唤醒
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            while (number == 0) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "\t 消费个蛋糕,还剩下" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 初始值为0
 * A线程生产一个
 * B线程消费一个
 * <p>
 * 来5轮
 */
public class ProdConsumer_Traditional {
    public static void main(String[] args) {
        TraditionalData traditionalData = new TraditionalData();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                traditionalData.increment();
            }
        }, "AA").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                traditionalData.decrement();
            }
        },"BB").start();
    }
}
