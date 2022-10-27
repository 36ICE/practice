package org.example.thread;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class CompareTest {


    private class MyReentrantLock extends AbstractQueuedSynchronizer {
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            while (true) {
                int c = getState();
                if (c == 0) {
                    if (compareAndSetState(0, acquires)) {
                        setExclusiveOwnerThread(current);
                        return true;
                    }
                }
            }
        }

        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
    }

    /**
     * 使用AQS框架
     */
    @Test
    public void test1() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        long begin = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 50; j++) {
                    reentrantLock.lock();
                    doBusiness();
                    reentrantLock.unlock();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        System.out.println("ReentrantLock cost : " + (System.currentTimeMillis() - begin));
    }

    /**
     * 无限重试
     */
    @Test
    public void test2() throws InterruptedException {
        MyReentrantLock myReentrantLock = new MyReentrantLock();
        long begin = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < 50; j++) {
                    myReentrantLock.tryAcquire(1);
                    doBusiness();
                    myReentrantLock.tryRelease(1);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        System.out.println("MyReentrantLock cost : " + (System.currentTimeMillis() - begin));
    }

    private void doBusiness() {
        // 空实现，模拟程序快速运行
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
