package com.example.mydubbo.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

public class EventLoopServer {
    public static void main(String[] args) {

        //细化2 :创建一个独立的EventLoop
        EventLoopGroup group=new DefaultEventLoopGroup();

        new ServerBootstrap()
                //细化1： boss只负责ServerSocketChannel 上的accept事件 worker只负责socketChannel上的读写
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
//                .childOption(ChannelOption.ALLOCATOR,new AdaptiveRecvByteBufAllocator((16,16,16)));
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //将来我们的handler执行时，使用的不再是worker中的线程，而是group中的线程，在这里我们handler1使用worker，而handler使用group
                        ch.pipeline().addLast("handler1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println(Thread.currentThread()+"  " +buf.toString(Charset.defaultCharset()));
                                //将消息传给下一个handler，否则不会传输过去
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast(group,"handler2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println(Thread.currentThread()+"  " +buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                }).bind(8888);

//        LineBasedFrameDecoder
//        DelimiterBasedFrameDecoder




    }
}
