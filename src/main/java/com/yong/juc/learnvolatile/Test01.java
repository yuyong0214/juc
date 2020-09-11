package com.yong.juc.learnvolatile;

public class Test01 {
    public volatile int n;
    public void add(){
        n++;
    }
}


