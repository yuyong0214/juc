package com.yong.juc.learncas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
        System.out.println("=========ABA问题========");
        new Thread(()->{
           atomicReference.compareAndSet(100,101);
           atomicReference.compareAndSet(101,100);
        },"t1").start();

        new Thread(()->{
            try { TimeUnit.SECONDS.sleep(1); } catch (Exception e) { e.printStackTrace(); }
            atomicReference.compareAndSet(100,2020);
            System.out.println("result "+ atomicReference.get());
        },"t2").start();


        System.out.println("=========没有ABA问题========");
        new Thread(()->{
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getReference(),atomicStampedReference.getReference()+1);
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getReference(),atomicStampedReference.getReference()+1);
        },"t3").start();

        new Thread(()->{
            try { TimeUnit.SECONDS.sleep(1); } catch (Exception e) { e.printStackTrace(); }
            atomicStampedReference.compareAndSet(100,2020,atomicStampedReference.getReference(),atomicStampedReference.getReference()+1);
            System.out.println("result "+ atomicStampedReference.getReference());
        },"t3").start();

    }
}
