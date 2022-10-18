package com.example.mydubbo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.InputStream;
import java.net.InetSocketAddress;

public class Demo1 {

    public static void main(String[] args) throws InterruptedException {
        //1.创建引导类
        ServerBootstrap b = new ServerBootstrap();
        //2.配置引导类的父子事件循环组
        EventLoopGroup parent=new NioEventLoopGroup();
        EventLoopGroup child = new NioEventLoopGroup();
        b.group(parent,child);
        //3.配置通道的IO类型
        b.channel(NioServerSocketChannel.class);

        //4.设置监听端口
        b.localAddress(new InetSocketAddress(9000));

        //5.设置传输通道的配置选项
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        // 配置连接timeout的时间
        b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);
        b.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        //6.配置子通道的pipeline
        b.childHandler(new ChannelInitializer<SocketChannel>() {
                           //有连接到达时会创建一个通道的子通道，并初始化
                           protected void initChannel(SocketChannel ch) {
                               //这里可以管理子通道中的Handler业务处理器
                               //向子通道流水线添加一个Handler业务处理器
                               ch.pipeline().addLast(new ChannelHandler() {
                                   @Override
                                   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                                       System.out.println("handlerAdded");
                                   }

                                   @Override
                                   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                                       System.out.println("handlerRemoved");
                                   }

                                   @Override
                                   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                       System.out.println("exceptionCaught");
                                   }
                               });
                               System.out.println("===");
                           }
                       });

        //7.绑定监听端口
        ChannelFuture channelFuture = b.bind();



        // 等待直到底层IO执行完毕
        assert channelFuture.isDone();

//        if (channelFuture.isCancelled()) {
//            // 用户手动取消Channel
//        } else if (!channelFuture.isSuccess()) {
//            channelFuture.cause().printStackTrace();
//        } else {
//            // 成功建立连接
//        }
        //8.自我阻塞，直到通道关闭的异步任务结束
        ChannelFuture closeFuture = channelFuture.channel().closeFuture();
        closeFuture.sync();

        //9.关闭EventLoopGroup
        // 释放掉所有资源，包括创建的反应器线程
        parent.shutdownGracefully();

        InputStream in = System.in;



    }
}
