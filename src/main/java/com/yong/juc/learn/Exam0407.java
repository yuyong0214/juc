package com.yong.juc.learn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Question01 {
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            // 1、判断
            while (number != 0) {
                condition.await();
            }
            // 2、干活
            System.out.println(Thread.currentThread().getName() + "\t 执行+1  " + number++);
            // 3、通知
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
            System.out.println(Thread.currentThread().getName() + "\t 执行-1  " + number--);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Question02 {
    // 0:A 1:B 2:C
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (number != 0){
                c1.await();
            }
            outPrint(5);
            number = 1;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            while (number != 1){
                c2.await();
            }
            outPrint(10);
            number = 2;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            while (number != 2){
                c3.await();
            }
            outPrint(15);
            number = 0;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void outPrint(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println(Thread.currentThread().getName() + "\t 打印第" + (i + 1) + "次");
        }
    }
}


public class Exam0407 {
    public static void main(String[] args) {
        //answer02();
    }

    /**
     * 2、多线程之间按照顺序调用，实现A->B->C
     * 三个线程启动，要求如下：
     * A打印5次，B打印10次，C打印15次
     */
    private static void answer02() {
        Question02 q = new Question02();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                q.print5();
            }, "A").start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                q.print10();
            }, "B").start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                q.print15();
            },"C").start();
        }
    }

    /**
     * 1、现在两个线程，可以操作初始值为零的一个变量
     * 实现一个线程对该变量加1，一个线程对该变量减1，
     * 实现交替，10轮，变量还是零
     */
    private static void answer01() {
        Question01 q = new Question01();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                q.increment();
            }, String.valueOf(i)).start();

            new Thread(() -> {
                q.decrement();
            }, String.valueOf(i)).start();
        }
    }
}
