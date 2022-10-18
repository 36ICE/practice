package com.example.mydubbo.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class TestPipeline {

    public static void main(String[] args) {

        //细化2 :创建一个独立的EventLoop
        EventLoopGroup group = new DefaultEventLoopGroup();

        new ServerBootstrap()
                //细化1： boss只负责ServerSocketChannel 上的accept事件 worker只负责socketChannel上的读写
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //将来我们的handler执行时，使用的不再是worker中的线程，而是group中的线程，在这里我们handler1使用worker，而handler使用group
                        ch.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        System.out.println(Thread.currentThread() + "  " + buf.toString(Charset.defaultCharset()));
                                        System.out.println("handler1");
                                        //将消息传给下一个handler，否则不会传输过去
                                        super.channelRead(ctx, msg);//等效于 ctx.fireChannelRead(msg);
                                    }
                                }).addLast(group, "handler2", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        System.out.println(Thread.currentThread() + "  " + buf.toString(Charset.defaultCharset()));
                                        System.out.println("handler2");
                                        super.channelRead(ctx, msg);//等效于  ctx.fireChannelRead(msg); 必须加，否则不会唤醒后面的handler进行处理
                                        // ，否则入站链在此处会断开

                                        //必须要写数据，否则后面的outboundHandler不会执行，从站链的尾部开始找出站处理器
//                                ch.writeAndFlush("server replay");
                                        //从当前处理器开始找出站处理器，放在此处不会执行
                                        // ctx.writeAndFlush("server replay");//
                                    }
                                })
                                .addLast("handler4", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        System.out.println("handler4");
                                        super.write(ctx, msg, promise);

                                    }
                                }).addLast("handler3", new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        System.out.println(Thread.currentThread() + "  " + buf.toString(Charset.defaultCharset()));
                                        System.out.println("handler3");
                                        //将消息传给下一个handler，否则不会传输过去
                                        super.channelRead(ctx, msg);//等效于 ctx.fireChannelRead(msg);
                                        ctx.writeAndFlush("server replay");
                                    }
                                }).addLast("handler5", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        System.out.println("handler5");
                                        super.write(ctx, msg, promise);
                                    }
                                }).addLast("handler6", new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        System.out.println("handler6");
                                        super.write(ctx, msg, promise);
                                    }
                                });
                    }
                }).bind(8888);


    }
}
