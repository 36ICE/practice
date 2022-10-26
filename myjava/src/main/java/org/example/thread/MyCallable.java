package org.example.thread;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MyCallable implements Callable<RunResult>{

    // 运算结果类
    private RunResult result;
    // 线程同步器
    private CountDownLatch main;
    @Override
    public RunResult call() throws Exception {
        String name = Thread.currentThread().getName();
//        System.out.println(name + " 线程开始执行" + "-" + result.getParam());
        for (int i = 0; i < 1; i++) {
            for (int j = 1; j <= 200000000; j++) {
                if (j == 200000000 && null != result.getParam()) {
                    result.setResult(result.getParam()*10);
                    result.setSuccess(true);
//                    System.out.println(name + " 线程正在进行计算" + "-" + result.getParam());
                } else {
                    result.setSuccess(false);
                }
            }
        }
//        System.out.println(name + " 线程执行完毕" + "-" + result.getParam());
        main.countDown();
        return result;
    }
    public MyCallable(RunResult result, CountDownLatch main) {
        super();
        this.result = result;
        this.main = main;
    }
    public MyCallable() {
        super();
    }
    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(6, 10, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(20),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        // 存放Future<>的集合
        List<Future<RunResult>> list = new ArrayList<Future<RunResult>>(12);
        // 线程同步器
        CountDownLatch main = new CountDownLatch(12);
        for (int i = 1; i <= 12; i++) {
            RunResult result = new RunResult();
            result.setParam(i);
            MyCallable callable = new MyCallable(result,main);
            Future<RunResult> runResultFuture = threadPool.submit(callable);
            list.add(runResultFuture);
        }
        try {
            main.await();
            for (int i = 0; i < list.size(); i++) {
                RunResult runResult = list.get(i).get();
                System.out.println(JSON.toJSON(runResult));
            }
            // 线程池不用了，关闭线程池
            threadPool.shutdown();
            //threadPool.shutdownNow();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }
    }


}
