package com.yong.juc.proxy.staticproxy;

public class Client {

    public static void main(String[] args) {
        TeacherDao teacherDao = new TeacherDao();
        TeacherDaoProxy teacherDaoProxy = new TeacherDaoProxy(teacherDao);
        System.out.println("before");
        teacherDaoProxy.teach();
        System.out.println("after");
    }
}
