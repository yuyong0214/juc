package com.yong.juc.learnvolatile;

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


/**
 * 1.验证volatile的可见性
 *  1.1 假如int number = 0; number变量之前没有添加volatile关键字修饰，没有可见性
 *  1.2 添加了volatile，可以解决可见性问题
 *
 * 2.验证volatile不保证原子性
 *  2.1 原子性是什么意思？  其实就是看最终一致性能不能保证
 *      不可分割，完整性，
 *      也即某个线程正在做某个业务业务的时候，中间不可以被加塞或者被分割，需要整体完整。
 *      要么同时成功，要么同时失败。
 *  2.2 答：因为有很多值在putfield这步写回去的时候可能线程的调度被挂起了，
 *      刚好也没有收到最新值的通知，有这么一个纳秒级别的时间差，一写就出现了写覆盖，就把人家的值覆盖掉了
 *
 *  2.2 volatile不保证原子性的案例演示
 *
 *  2.3 volatile为什么不能保证原子性?
 *
 *  2.4 如何解决原子性？
 *      * 1.可以加synchronized来解决不保证原子性问题，但是不推荐使用
 *      * 2.可以使用java.util.concurrent.atomic包下的AtomicInteger(带原子包装的整型类)来解决不保证原子性问题
 * 3、禁止指令重排/有序性
 *  
 */
public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.myAtomic();
                }
            },String.valueOf(i)).start();
        }
        while (Thread.activeCount()>2){
            Thread.yield();
        }
        System.out.println(myData.number);
        System.out.println(myData.atomicInteger);
    }

    private static void OldMethod() {
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
