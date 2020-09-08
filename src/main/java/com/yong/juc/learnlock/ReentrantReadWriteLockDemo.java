package com.yong.juc.learnlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCacheDemo {
    private volatile Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "\t 正在写入 " + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "\t 写入完成");
    }

    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "\t 正在读取");
        map.get(key);
        System.out.println(Thread.currentThread().getName() + "\t 读取成功");
    }

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void lockPut(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入 " + key);
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void lockGet(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
    }
}

public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyCacheDemo myCacheDemo = new MyCacheDemo();
        for (int i = 0; i < 5; i++) {
            final int temInt = i;
            new Thread(() -> {
                // myCacheDemo.put(temInt + "", temInt + "");
                myCacheDemo.lockPut(temInt + "", temInt + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int temInt = i;
            new Thread(() -> {
                //myCacheDemo.get(temInt + "");
                myCacheDemo.lockGet(temInt + "");
            }, String.valueOf(i)).start();
        }
    }
}
