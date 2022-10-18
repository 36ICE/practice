package com.example.mydubbo.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class CloseFutureClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
//                .option()
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));//打印日志
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                //连接服务器
                .connect(new InetSocketAddress("localhost", 8888));

        //为什么要调用sync，
        //1.connect方法，是异步非阻塞的，不关心结果，是另外一个线程返回结果，
        //2.
        channelFuture .sync();
        Channel channel = channelFuture.channel();
        //发送数据
        new Thread(()->{

            Scanner scanner=new Scanner(System.in);
            while (true){
                String line =scanner.nextLine();
                if("q".equals(line)){
                    channel.close();

                    break;
                }
                channel.writeAndFlush("hello");
            }
        },"start").start();

        //获取CloseFuture对象， 1）同步处理关闭 2）异步处理关闭
        ChannelFuture closeFuture = channel.closeFuture();
        System.out.println("waiting close");
        closeFuture.sync();
        log.debug("关闭之后的操作");
        System.out.println("关闭之后的操作");
        eventLoopGroup.shutdownGracefully();


        //第二种方式
        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.debug("关闭之后的操作");
                System.out.println("关闭之后的操作");
                eventLoopGroup.shutdownGracefully();//拒绝新的任务，执行完现有的任务
            }
        });


        //为什么close之后，主线程还没结束，主要是因为nio eventloop中有些内部线程未结束  eventLoopGroup.shutdownGracefully();
        //服务端同样要这么处理

        //netty中很多方法都是异步，所以我们要抛弃之前的很多思维，直接在代码后添加东西，需要使用sync 和future等方式来处理

    }
}
