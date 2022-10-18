package com.example.mydubbo.netty.eventloop;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;

import java.util.concurrent.TimeUnit;

public class TestEventLoop {

    public static void main(String[] args) {
        //1.创建事件循环组
        EventLoopGroup group=new NioEventLoopGroup(2);//io 事件 普通任务 定时任务
        DefaultEventLoopGroup eventExecutors = new DefaultEventLoopGroup();//处理普通任务，定时任务，不能处理IO事件

        System.out.println(NettyRuntime.availableProcessors());

        //2.获取下一个事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        //3.执行普通任务
        group.next().execute(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello "+Thread.currentThread());
        });


        //4.定时任务 延后或者间隔一段时间执行一个任务 .场景做keepalive时保证存活
        group.next().scheduleAtFixedRate(()->{
            System.out.println("hi "+Thread.currentThread());
        },0,1, TimeUnit.SECONDS);



        System.out.println(Thread.currentThread());



    }
}
