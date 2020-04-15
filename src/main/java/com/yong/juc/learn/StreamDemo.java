package com.yong.juc.learn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private Integer id;
    private String username;
    private int age;

}

/**
 * 题目：请按照给出数据，找出同时满足以下条件的用户，也即以下条件全部满足
 * 偶数ID
 * 且年龄大于24
 * 且用户名转为大写
 * 且用户名字母到排序
 * 只输出一个用户名字
 */
public class StreamDemo {
    public static void main(String[] args) {
        User u1 = new User(11, "a", 23);
        User u2 = new User(12, "b", 24);
        User u3 = new User(13, "c", 22);
        User u4 = new User(14, "d", 28);
        User u5 = new User(16, "e", 26);

        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);
        list.forEach(System.out::println);

        System.out.println("========================");

        list.stream().filter(u -> u.getId() % 2 == 0)
                .filter(u -> u.getAge() > 24)
                .map(u -> u.getUsername().toUpperCase())
                .sorted((s1,s2)->s2.compareTo(s1)).limit(1).forEach(System.out::println);
    }
}
