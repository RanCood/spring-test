package com.zg.st.spring.entity;

/**
 * @author: zg
 * @date: 2020/6/12 17:35
 */
public class User {
    private String name;
    private Integer age;
    private Integer gender;

    public User() {
    }

    public User(String name, Integer age, Integer gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
