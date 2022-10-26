package org.example.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class MyDelay<T> implements Delayed {

    //延迟时间s
    private long delayTime;
    //过期时间
    long currentTime=System.currentTimeMillis();

    T t;

    public MyDelay(long delayTime,T t) {

        this.delayTime=currentTime+delayTime*1000;
        this.t=t;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(delayTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        long f=getDelay(TimeUnit.MILLISECONDS)-o.getDelay(TimeUnit.MILLISECONDS);
        return (int) f;
    }

    @Override
    public String toString() {
        return "MyDelay{" +
                "delayTime=" + delayTime +
                ", currentTime=" + currentTime +
                ", t=" + t +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {


        DelayQueue<Delayed> delayeds = new DelayQueue<>();
        delayeds.add(new MyDelay<>(2,"2"));
        delayeds.add(new MyDelay<>(3,"3"));
        delayeds.add(new MyDelay<>(10,"10"));



        while (!delayeds.isEmpty()){

            new Thread(()->{
                Delayed delayed = null;
                try {
                    delayed = delayeds.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(delayed);
            }).start();
        }
    }
}
