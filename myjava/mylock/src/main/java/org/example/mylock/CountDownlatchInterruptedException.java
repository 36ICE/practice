package org.example.mylock;


import java.util.concurrent.CountDownLatch;

/**
 *
 * 什么情况下countdownlatch会抛出InterruptedException
 *
 */
public class CountDownlatchInterruptedException {



    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch=new CountDownLatch(4);
        Thread.currentThread().interrupt();
        countDownLatch.await();
    }

}
