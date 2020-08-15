package com.junmeng.android_java_example;

import java.util.Observable;

public class UserBean extends Observable {


    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyObservers();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyObservers();
    }
}
