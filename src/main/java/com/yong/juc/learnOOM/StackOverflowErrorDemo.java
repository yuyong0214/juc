package com.yong.juc.learnOOM;

/**
 * 栈 的默认大小是 512k ~ 1024K
 * Exception in thread "main" java.lang.StackOverflowError
 * 栈溢出
 * java.lang.StackOverflowError 是错误
 *
 *
 * 组织架构
 * java.lang.Object
 *  java.lang.Throwable
 *      java.lang.Error
 *          java.lang.VirtualMachineError   java虚拟机错误
 *              java.lang.StackOverflowError
 */
public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        stackOverflowError();
    }

    private static void stackOverflowError() {
        stackOverflowError();
    }
}
