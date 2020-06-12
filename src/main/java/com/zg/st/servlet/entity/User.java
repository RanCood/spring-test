package com.zg.st.servlet.entity;

/**
 * @author: zg
 * @date: 2020/6/10 15:41
 */
public class User {
    public long id;
    public String name;
    public School school;

    public User() {
    }

    public User(long id, String name, School school) {
        this.id = id;
        this.name = name;
        this.school = school;
    }
}
