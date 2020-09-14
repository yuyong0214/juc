package com.yong.juc.learnlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone {
    public synchronized void sendSms() {
        System.out.println(Thread.currentThread().getName() + "\t sendSms");
        sendEmail();
    }

    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t sendEmail");
    }
}

class iPhone implements Runnable {

    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    private void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get method");
            set();
        } finally {
            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t set method");
        } finally {
            lock.unlock();
        }
    }
}

/**
 * Description:
 * 可重入锁(也叫做递归锁)
 * 指的是同一线程外层函数获得锁后,内层递归函数仍然能获取该锁的代码
 * 在同一线程外层方法获取锁之后,在进入内层方法会自动获取锁
 * <p>
 * 也就是说,线程可以进入任何一个它已经标记的锁所同步的代码块
 */
public class ReenterLockDemo {
    public static void main(String[] args) {
        //phoneDemo();
        iPhone ip = new iPhone();
        Thread t1 = new Thread(ip);
        Thread t2 = new Thread(ip);

        t1.start();
        t2.start();
    }

    private static void phoneDemo() {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendSms();
        }, "t1").start();

        new Thread(() -> {
            phone.sendSms();
        }, "t2").start();
    }
}
