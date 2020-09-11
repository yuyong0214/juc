package com.yong.juc.proxy.staticproxy;

public class TeacherDaoProxy implements ITeacherDao {
    ITeacherDao target;

    public TeacherDaoProxy(ITeacherDao target) {
        this.target = target;
    }

    @Override
    public void teach() {
        target.teach();
    }
}
