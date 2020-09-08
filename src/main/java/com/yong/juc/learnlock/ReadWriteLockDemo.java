package com.yong.juc.learnlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCatch {
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    final private Map map = new HashMap();

    public void put() {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t ------准备插入数据");
            map.put(Thread.currentThread().getName(), Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + "\t ------插入数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 准备读取数据");
            map.get(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + "\t 读取数据成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCatch myCatch = new MyCatch();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myCatch.put();
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                myCatch.get();
            }, String.valueOf(i)).start();
        }
    }
}
