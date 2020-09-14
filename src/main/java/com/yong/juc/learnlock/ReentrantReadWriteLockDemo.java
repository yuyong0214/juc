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

/**
 * Description:
 * 多个线程同时操作 一个资源类没有任何问题 所以为了满足并发量
 * 读取共享资源应该可以同时进行
 * 但是
 * 如果有一个线程想去写共享资源来  就不应该有其他线程可以对资源进行读或写
 * <p>
 * 小总结:
 * 读 读能共存
 * 读 写不能共存
 * 写 写不能共存
 * 写操作 原子+独占 整个过程必须是一个完成的统一整体 中间不允许被分割 被打断
 *
 **/
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
