package com.example.myredission;

import java.util.concurrent.*;

public class MyRateLimiter3 {

    private static ExecutorService executorService =
            new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(), r -> {
                        Thread sub = new Thread(new ThreadGroup("Sub-group"), r, "Sub");
//                        sub.setDaemon(false);
                        return sub;
                    }, new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(10);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    executorService.submit(() -> {
                        try {
                            System.out.println("线程" + Thread.currentThread().getName()+Thread.currentThread().getId() + "进入数据区："+Thread.currentThread().isDaemon() + System.currentTimeMillis());
//                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            latch.countDown();
                        }
                    });
                }
                try {
                    latch.await();
                    System.out.println("结束了");
//                        executorService.shutdown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        System.out.println(Thread.currentThread().isDaemon());
        //如何等任务结束关闭线程池
//        executorService.shutdown();
        //executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

//


    }
}
