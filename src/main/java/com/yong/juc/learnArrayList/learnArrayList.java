package com.yong.juc.learnArrayList;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class learnArrayList {
    public static void main(String[] args) {
        notSafeArrayList();
    }

    /**
     *
     * 笔记
     * 写时复制 copyOnWrite 容器即写时复制的容器 往容器添加元素的时候,不直接往当前容器object[]添加,
     * 而是先将当前容器object[]进行 copy 复制出一个新的object[] newElements
     * 然后向新容器object[] newElements 里面添加元素 添加元素后,
     * 再将原容器的引用指向新的容器 setArray(newElements);
     * 这样的好处是可以对copyOnWrite容器进行并发的读,而不需要加锁 因为当前容器不会添加任何容器.
     * 所以copyOnwrite容器也是一种 读写分离的思想,读和写不同的容器.
     *     public boolean add(E e) {
     *         final ReentrantLock lock = this.lock;
     *         lock.lock();
     *         try {
     *             Object[] elements = getArray();
     *             int len = elements.length;
     *             Object[] newElements = Arrays.copyOf(elements, len + 1);
     *             newElements[len] = e;
     *             setArray(newElements);
     *             return true;
     *         } finally {
     *             lock.unlock();
     *         }
     *     }
     *
     * 有以下代码展示ArrayList、ArraySet、HashMap 都是线程不安全的
     */
    private static void notSafeArrayList() {
        new CopyOnWriteArraySet<>();
        new ConcurrentHashMap<>();
        List list = new CopyOnWriteArrayList();//new Vector();//Collections.synchronizedList(new ArrayList<>());//new ArrayList();
        for (int i = 1; i <= 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
