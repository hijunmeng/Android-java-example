package com.junmeng.mylibrary;


public class TestWithoutKeep {

    public String name;
    public int age;

    public TestWithoutKeep(String name,int age){
        this.name=name;
        this.age=age;
    }

    @Override
    public String toString() {
        return "TestWithoutKeep{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
