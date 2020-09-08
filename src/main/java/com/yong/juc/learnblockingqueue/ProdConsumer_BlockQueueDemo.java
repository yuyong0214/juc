package com.yong.juc.learnblockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class BlockQueueData {
    private volatile boolean FLAG = true; // 默认开启，进行生产+消费
    // 多线程交互需要保持原子性
    private AtomicInteger atomicInteger = new AtomicInteger();

    // 采用封装者模式
    BlockingQueue<String> blockingQueue = null;

    public BlockQueueData(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        // 通过类加载反射机制获取对象
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProd() throws InterruptedException {
        String result = "";
        boolean resultStatus;
        while (FLAG) {
            result = atomicInteger.getAndIncrement() + "";
            resultStatus = blockingQueue.offer(result, 2L, TimeUnit.SECONDS);
            if (resultStatus) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + result + "成功");
            }else{
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + result + "失败");
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t boos 叫停了，表示FLAG=false,生产动作失败");
    }

    public void myConsumer() throws InterruptedException {
        String result = "";
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (result == null || result.equalsIgnoreCase("")) {
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒没有获取到蛋糕，消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
        }


    }

    public void stop() {
        this.FLAG = false;
    }

}

/**
 * volatile/CAS/atomicInteger/BlockQueue/线程交互/原子引用
 */
public class ProdConsumer_BlockQueueDemo {
    public static void main(String[] args) {
        BlockQueueData blockQueueData = new BlockQueueData(new ArrayBlockingQueue(10));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try {
                blockQueueData.myProd();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try {
                blockQueueData.myConsumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        blockQueueData.stop();
    }
}
