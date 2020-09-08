package com.yong.juc.learnblockingqueue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        /**
         *  异常组 add  remove  element
         *  当阻塞队列满时，再往队列中 add 插入元素时会抛出异常 java.lang.IllegalStateException: Queue full
         *  当阻塞队列空时，再往队列中 remove/element 移除/检查元素时会抛出异常 java.util.NoSuchElementException
         */
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //System.out.println(blockingQueue.remove());

        System.out.println("exception =================================");

        /**
         * 特殊值（Boolean 组） offer  poll  peek
         * 插入成功返回 true 插入失败返回 false
         * 移除成功返回 true 移除失败返回 false
         */
        BlockingQueue<String> blockingQueue1 = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue1.offer("a"));
        System.out.println(blockingQueue1.offer("b"));
        System.out.println(blockingQueue1.offer("c"));
        System.out.println(blockingQueue1.offer("d"));

        System.out.println(blockingQueue1.peek());

        System.out.println(blockingQueue1.poll());
        System.out.println(blockingQueue1.poll());
        System.out.println(blockingQueue1.poll());
        System.out.println(blockingQueue1.poll());

        System.out.println("boolean ========================================");

        /**
         * 阻塞组   put   take
         * 当阻塞队列满时，生产线程继续put元素时，队列会一直阻塞，一直到成功put值或者响应中断退出
         * 当阻塞队列空时，消费线程继续take元素时，队列会一直阻塞，一直到成功take值或者响应中断退出
         */

        BlockingQueue<String> blockingQueue2 = new ArrayBlockingQueue<>(3);
        blockingQueue2.put("a");
        blockingQueue2.put("b");
        blockingQueue2.put("c");
        // 会被一直阻塞直到成功
        //blockingQueue2.put("d");
        System.out.println(blockingQueue2.take());
        System.out.println(blockingQueue2.take());
        System.out.println(blockingQueue2.take());
        // 会被一直阻塞直到成功
        //blockingQueue2.take();

        System.out.println("block ===========================================");

        /**
         * 超时组  offer(e,time,util)  poll(time,util)
         * 当阻塞队列满时，队列会阻塞生产者一定时间，超过限时后，生产者会自动退出
         * 当阻塞队列空时，队列会阻塞消费者一定时间，超过限时后，消费者会自动退出
         */
        BlockingQueue<String> blockingQueue3 = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue3.offer("a", 1, TimeUnit.SECONDS));
        System.out.println(blockingQueue3.offer("b", 1, TimeUnit.SECONDS));
        System.out.println(blockingQueue3.offer("c", 1, TimeUnit.SECONDS));
        System.out.println(blockingQueue3.offer("d", 1, TimeUnit.SECONDS));

        System.out.println(blockingQueue3.poll(1, TimeUnit.SECONDS));
        System.out.println(blockingQueue3.poll(1, TimeUnit.SECONDS));
        System.out.println(blockingQueue3.poll(1, TimeUnit.SECONDS));
        System.out.println(blockingQueue3.poll(1, TimeUnit.SECONDS));

    }
}
