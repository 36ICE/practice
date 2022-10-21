package com.example.useprocessor;


import org.example.practice2.Getter;

@Getter
public class Person {

    private String name;

    public Person(String name) {
        this.name=name;
    }

    public static void main(String[] args) {
        System.out.println("==");
        Person zq = new Person("zq");
//        System.out.println(zq.getName());
    }
}
