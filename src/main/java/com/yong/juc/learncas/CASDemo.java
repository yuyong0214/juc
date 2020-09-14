package com.yong.juc.learncas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.什么是CAS ? ===> compareAndSwap 比较并交换
 *
 * 1.如果线程的期望值跟物理内存的真实值一样，就更新值到物理内存当中，并返回true
 * 2.如果线程的期望值跟物理内存的真实值不一样，返回是false，那么本次修改失败，那么此时需要重新获得主物理内存的新值
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger.compareAndSet(0, 2020) + "\t atomicInteger value is " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(0, 2021) + "\t atomicInteger value is " + atomicInteger.get());
    }
}
