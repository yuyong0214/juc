package com.yong.juc.learn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PrintClass {
    private int number = 1; // 标识  1：A线程执行；2：B线程执行；3：C线程执行
    private Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void print(int n) throws InterruptedException {
        lock.lock();
        try {
            Condition c = null;
            Condition nextC = null;
            // 判断
            while (number != 1) {
                c = c1;
                nextC = c2;
                c.await();
            }
            // 干活
            for (int i = 0; i < n; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 通知
            number = 2;
            nextC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print5() throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (number != 1) {
                c1.await();
            }
            // 干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 通知
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void print10() throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (number != 2) {
                c2.await();
            }
            // 干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 通知
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    public void print15() throws InterruptedException {
        lock.lock();
        try {
            // 判断
            while (number != 3) {
                c3.await();
            }
            // 干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 通知
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 备注：多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 * <p>
 * A打印5次，B打印10次，C打印15次
 * 接着
 * A打印5次，B打印10次，C打印15次
 * 来10轮
 */
public class ShareData {
    public static void main(String[] args) {
        PrintClass pc = new PrintClass();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    pc.print5();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    pc.print10();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    pc.print15();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
    }
}
