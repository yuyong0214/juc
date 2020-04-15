package com.yong.juc.learn;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {
    public static final int NUMBER = 10;

    private Integer begin;
    private Integer end;
    private Integer result = 0;

    public MyTask(Integer begin, Integer end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) <= NUMBER) {
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            Integer middle = (begin + end) / 2;
            MyTask m1 = new MyTask(begin, middle);
            MyTask m2 = new MyTask(middle + 1, end);
            m1.fork();
            m2.fork();
            result = m1.join() + m2.join();
        }

        return result;
    }
}

/**
 * 分支合并框架
 * ForkJoinPool
 * ForkJoinTask
 * RecursiveTask
 * <p>
 * 计算 0~100 的和
 */
public class ForkJoinPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask m = new MyTask(0, 100);
        ForkJoinPool threadPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = threadPool.submit(m);
        System.out.println(submit.get());
        threadPool.shutdown();
    }
}
