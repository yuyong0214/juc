package com.yong.juc.learn;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JUCExplain {
    /**
     * java.util.concurrent 并发包
     * java.util.concurrent.atomic 并发原子
     * java.util.concurrent.locks  并发锁
     */
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        AtomicInteger atomicInteger = new AtomicInteger();
    }
}
