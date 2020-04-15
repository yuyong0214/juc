package com.yong.juc.learn;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 开会，直到最后一个人员到齐后，才开始
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("***召唤神龙***");
        });
        for (int i = 1; i <= 7; i++) {
            final int count = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 找到了"+ count +"星的的龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
