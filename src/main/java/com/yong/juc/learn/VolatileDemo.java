package com.yong.juc.learn;

import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    public static int number = 0;
    public void addPlusPlus(){
        number++;
    }
    AtomicInteger atomicInteger = new AtomicInteger();
    public void myAtomic(){
        atomicInteger.getAndIncrement();
    }
}
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.myAtomic();
                }
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+"\t int type,finally number value :"+myData.number);
        System.out.println(Thread.currentThread().getName()+"\t AtomicInteger type,finally number value :"+myData.atomicInteger);
    }
}
