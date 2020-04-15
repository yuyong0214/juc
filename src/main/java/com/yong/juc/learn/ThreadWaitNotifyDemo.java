package com.yong.juc.learn;

class MyNumber {
    int number = 0;

    public synchronized void increment() throws InterruptedException {
        // 判断
        while (number != 0) {
            this.wait();
        }
        // 干活
        System.out.println(Thread.currentThread().getName() + "\t" + number++);
        // 通知
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while (number == 0) {
            this.wait();
        }
        System.out.println(Thread.currentThread().getName() + "\t" + number--);
        this.notifyAll();
    }
}

/**
 * 现在两个线程，可以操作初始值为零的一个变量
 * 实现一个线程对该变量加1，一个线程对该变量减1，
 * 实现交替，10轮，变量还是零
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myNumber.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myNumber.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
    }
}
