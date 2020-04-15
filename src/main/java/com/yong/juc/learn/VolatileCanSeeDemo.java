package com.yong.juc.learn;

import java.util.concurrent.TimeUnit;

class VSeeDemo {
    public volatile int number = 0;

    public void add60() {
        number = 60;
    }
}

public class VolatileCanSeeDemo {
    public static void main(String[] args) {
        VSeeDemo vSeeDemo = new VSeeDemo();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vSeeDemo.add60();
            System.out.println(Thread.currentThread().getName()+"\t updated number value :"+vSeeDemo.number);
        }, "this is thread name").start();
        while (vSeeDemo.number == 0) {
        }
        System.out.println(Thread.currentThread().getName()+"\t mission is over");
    }
}
