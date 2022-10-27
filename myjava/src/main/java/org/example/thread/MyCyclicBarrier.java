package org.example.thread;

import java.util.Random;
import java.util.concurrent.*;

public class MyCyclicBarrier {


    public static void main(String[] args) throws InterruptedException {
        // 创建线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(10, 20,
                0, TimeUnit.SECONDS, new LinkedBlockingDeque<>(1024));
        final int taskCount = 5;    // 任务总数
        // 循环计数器 ①
        CyclicBarrier cyclicBarrier = new CyclicBarrier(taskCount, new Runnable() {
            @Override
            public void run() {
                // 线程池执行完
                System.out.println();
                System.out.println("线程池所有任务已执行完！");
            }
        });
        // 添加任务
        for (int i = 0; i < taskCount; i++) {
            final int finalI = i;
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 随机休眠 0-4s
                        int sleepTime = new Random().nextInt(5);
                        TimeUnit.SECONDS.sleep(sleepTime);
                        System.out.println(String.format("任务%d执行完成", finalI));
                        // 线程执行完
                        cyclicBarrier.await(); // ②
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
