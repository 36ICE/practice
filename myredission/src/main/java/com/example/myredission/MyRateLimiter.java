package com.example.myredission;

import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;

public class MyRateLimiter {


    public static void main(String[] args) {
        RedissonClient redissonClient = Redisson.create();

        // 1、 声明一个限流器
        RRateLimiter rRateLimiter= redissonClient.getRateLimiter("ratelimiter");
        // 2、 设置速率，5秒中产生3个令牌
        rRateLimiter.trySetRate(RateType.OVERALL, 3, 5, RateIntervalUnit.SECONDS);

        // 3、试图获取一个令牌，获取到返回true
        rRateLimiter.tryAcquire(1);
    }
}
