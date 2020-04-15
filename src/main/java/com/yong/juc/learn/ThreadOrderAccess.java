package com.yong.juc.learn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class OrderAccess{
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    private int n = 1;

    public void print5(){
        lock.lock();
        try {
            while (n!=1){
                c1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            n = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            while (n!=2){
                c2.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            n = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            while (n!=3){
                c3.await();
            }
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName());
            }
            n = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 多线程之间按照顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 * A打印5次，B打印10次，C打印15次
 * 然后
 * A打印5次，B打印10次，C打印15次
 * 共10轮
 */
public class ThreadOrderAccess {
    public static void main(String[] args) {
        OrderAccess orderAccess = new OrderAccess();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                orderAccess.print5();
            }
        },"AA").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                orderAccess.print10();
            }
        },"BB").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                orderAccess.print15();
            }
        },"CC").start();
    }
}
