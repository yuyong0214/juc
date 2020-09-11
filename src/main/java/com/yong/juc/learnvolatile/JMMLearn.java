package com.yong.juc.learnvolatile;

public class JMMLearn {
    public static void main(String[] args) {
        /**
         * JMM java memory model java内存模型，本身是一种抽象概念并不真实存在。
         * 共享变量都存放于主内存，线程需要从主内存中读取共享变量，然后在自己的工作内存中对此变量进行操作
         * 操作完成后会重新写回主内存。工作内存之间不能直接通信必须要通过主内存来通信。
         *
         * 可见性问题的由来：
         *  通过JMM可以知道线程需要在自己的工作内存中操作后然后写回主内存。
         *  A、B两个线程同时获取主内存中的共享变量X并操作，A、B两个线程中工作内存中的内容并不相互可见。
         */
    }
}
