package com.example.myredission;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRateLimiter2 {


    public static void main(String[] args) throws InterruptedException {

        RateLimiter rateLimiter = RateLimiter.create(2);
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            executorService.submit(()->{
                try{
                    rateLimiter.acquire();
                    System.out.println("线程"+Thread.currentThread().getId()+"进入数据区："+System.currentTimeMillis());
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }


    }
}
