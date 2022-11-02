package org.example.lang;

import jdk.internal.misc.SharedSecrets;

public class Clazz {


    public static void main(String[] args) {
        //不能直接访问
//        Clazz.class.getCon

        System.out.println(SharedSecrets.getJavaLangAccess());


    }
}
