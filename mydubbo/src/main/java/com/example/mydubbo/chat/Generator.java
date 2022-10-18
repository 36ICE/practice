package com.example.mydubbo.chat;

import java.util.concurrent.atomic.AtomicInteger;

public class Generator {

    private static final AtomicInteger atomicInteger=new AtomicInteger();

    public static int nextId(){

        return atomicInteger.incrementAndGet();
    }

}
