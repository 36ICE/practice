package org.example.sentinel.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GuavaRateLimiter {

    public static void main(String[] args) throws InterruptedException {
        // qps 2
        RateLimiter rateLimiter = RateLimiter.create(2);
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
            System.out.println(time + ":" + rateLimiter.tryAcquire());
            Thread.sleep(250);
        }
    }
}
