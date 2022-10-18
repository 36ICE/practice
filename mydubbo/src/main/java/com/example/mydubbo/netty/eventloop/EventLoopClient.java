package com.example.mydubbo.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class EventLoopClient {

    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
//                .option()
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
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
        channel.writeAndFlush("hello");

    }
}
