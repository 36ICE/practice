package org.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Threadlimiter {
    public static final ThreadPoolExecutor workThreadPool = new ThreadPoolExecutor(
            5,
            32,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(500),
            r -> new Thread(r, "zcd-threadlimiter-workThreadPool-" + r.hashCode()),
            (r, e) -> {
                throw new RejectedExecutionException("Task " + r.toString() +
                        " rejected from " +
                        e.toString());
            });
    public static final ThreadPoolExecutor bossThreadPool = new ThreadPoolExecutor(
            100,
            100,
            2L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(50),
            r -> new Thread(r, "zcd-threadlimiter-bossThreadPool-" + r.hashCode()),
            (r, e) -> {
                throw new RejectedExecutionException("Task " + r.toString() +
                        " rejected from " +
                        e.toString());
            });
    public static void main(String[] args) throws InterruptedException {

        //任务数
        int taskNum = 100;

        //使用线程数量
        int useThreadNum = 5;
        // 线程同步器
        CountDownLatch bossCountDownLatch = new CountDownLatch(taskNum);
        CountDownLatch workCountDownLatch = new CountDownLatch(taskNum);
        for (int t = 0; t < taskNum; t++) {
            int finalT = t;
            bossThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    // 存放Future<>的集合
                    List<Future<RunResult>> list = new ArrayList<Future<RunResult>>(12);
                    // 线程同步器
                    CountDownLatch threadCountDownLatch = new CountDownLatch(useThreadNum);
                    for (int i = 0; i < useThreadNum; i++) {
                        RunResult result = new RunResult();
                        result.setParam(finalT);
                        MyCallable callable = new MyCallable(result, threadCountDownLatch);
                        Future<RunResult> runResultFuture = workThreadPool.submit(callable);
                        list.add(runResultFuture);

                    }
                    try {
                        threadCountDownLatch.await();
                        System.out.println("任务 "+finalT+"执行完毕");
//                        for (int i = 0; i < list.size(); i++) {
//                            RunResult runResult = list.get(i).get();
//                            System.out.println(JSON.toJSON(runResult));
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    workCountDownLatch.countDown();
                }
            });
            bossCountDownLatch.countDown();

        }



        //boss线程结束
        bossCountDownLatch.await();
        System.out.println("boss线程结束");

        workCountDownLatch.await();
        System.out.println("work线程结束");

        while (true){

        }

    }
}
