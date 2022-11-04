package com.example.myredission;

import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRateLimiter1 {


    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create();

        // 1、 声明一个限流器
        RRateLimiter rRateLimiter= redissonClient.getRateLimiter("ratelimiter");
        // 2、 设置速率，5秒中产生3个令牌
//        rRateLimiter.trySetRate(RateType.OVERALL, 3, 3, RateIntervalUnit.SECONDS);

        rRateLimiter.trySetRate(RateType.PER_CLIENT,5,2, RateIntervalUnit.MINUTES);

        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            executorService.submit(()->{
                try{
                    rRateLimiter.acquire();
                    System.out.println("线程"+Thread.currentThread().getId()+"进入数据区："+System.currentTimeMillis());
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }


    }
}
