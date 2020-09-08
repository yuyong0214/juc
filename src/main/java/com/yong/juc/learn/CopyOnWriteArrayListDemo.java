package com.yong.juc.learn;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 1、故障现象
 * java.util.ConcurrentModificationException 并发修改异常
 * 2、故障原因
 * 由于多线程间对同一资源同时进行读 和 写操作，而拉扯造成的问题
 * 3、解决办法
 *  3.1 Vector() 采用Vector方法（不建议；因为使用的synchronized重锁，读和写都加锁了，严重影响效率）
 *  3.2 Collections.synchronized(new ArrayList()) 采用的是同步代码块
 *  3.3 CopyOnWriteArrayList() 采用写时复制，也就是说读可以随便读，但是写的时候需要复制一份，在复制的内容上修复，写完后需要覆盖原来的版本。
 * 4、优化建议
 */
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        List list = new CopyOnWriteArrayList(); //Collections.synchronizedList(new ArrayList<>());//new Vector(); //new ArrayList();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
