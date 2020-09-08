package com.yong.juc.learnthread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MySignalNumber {
    int number = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            while (number != 0) {
                condition.await();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + number++);
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
            System.out.println(Thread.currentThread().getName() + "\t" + number--);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 现在两个线程，可以操作初始值为零的一个变量
 * 实现一个线程对该变量加1，一个线程对该变量减1，
 * 实现交替，10轮，变量还是零
 */
public class ThreadAwaitSignalDemo {
    public static void main(String[] args) {
        MySignalNumber mySignalNumber = new MySignalNumber();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySignalNumber.increment();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySignalNumber.decrement();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySignalNumber.increment();
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                mySignalNumber.decrement();
            }
        }, "D").start();
    }
}
