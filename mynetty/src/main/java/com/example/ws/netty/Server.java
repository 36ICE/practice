package com.example.ws.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server
{
    public static void main( String[] args ) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
            @Override
            public void initChannel(SocketChannel ch)throws Exception{
                ch.pipeline().addLast(new EchoServerHandler());
            }
        });

        ChannelFuture f = bootstrap.bind(8080).addListener((ChannelFutureListener) future -> {
            System.out.println("bind success in port: " + 8080);
        }).sync();
        f.channel().closeFuture().sync();
        System.out.println("server started!");
    }

}
