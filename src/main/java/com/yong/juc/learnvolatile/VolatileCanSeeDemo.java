package com.yong.juc.learnvolatile;

import java.util.concurrent.TimeUnit;

class VSeeDemo {
    public volatile int number = 0;

    public void add60() {
        number = 60;
    }
}

public class VolatileCanSeeDemo {
    /**
     * volatile 是java轻量级的同步机制，基本上遵循了JMM规范，
     * 主要是保证了可见性和禁止指令重排，但是并不保证原子性
     * volatile 3大特点
     *  1、保证可见性
     *  2、不保证原子性
     *  3、禁止指令重排
     */



    public static void main(String[] args) {
        //YouCanSee();
        VSeeDemo vSeeDemo = new VSeeDemo();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in "+vSeeDemo.number);
            try { TimeUnit.SECONDS.sleep(4); } catch (Exception e) { e.printStackTrace(); }
            vSeeDemo.add60();
            System.out.println(Thread.currentThread().getName()+"\t change number to 60 "+vSeeDemo.number);
        },"AAA").start();

        while (vSeeDemo.number == 0){

        }

        System.out.println(Thread.currentThread().getName()+"\t number is "+vSeeDemo.number);
    }

    private static void YouCanSee() {
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
