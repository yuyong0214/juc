package com.yong.juc.learncas;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

@Data
@AllArgsConstructor
class User{
    private String name;
    private int age;
}
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User zs = new User("zs", 12);
        User ls = new User("ls", 23);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(zs);
        System.out.println(atomicReference.compareAndSet(zs, ls));
        System.out.println(atomicReference.compareAndSet(zs, ls));
        System.out.println(atomicReference.get());
    }
}
