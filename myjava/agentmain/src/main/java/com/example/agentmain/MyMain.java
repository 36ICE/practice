package com.example.agentmain;

import java.lang.management.ManagementFactory;

public class MyMain {

    public static void main(String[] args) {

        String name = ManagementFactory.getRuntimeMXBean().getName();
        String vmName = ManagementFactory.getRuntimeMXBean().getVmName();
        String pid = name.split("@")[0];
        while (true){
            try {
                System.out.println("MyMain :"+MyMain.class+" pid: "+pid+" name"+name+" vmName"+vmName);
                Thread.sleep(2000);
                Person person = new Person();
                person.test();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
