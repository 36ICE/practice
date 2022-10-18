package com.example.mydubbo.netty.eventloop;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TestEmbeddedChannel {

    public static void main(String[] args) {
        ChannelInboundHandlerAdapter handler1 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                System.out.println(Thread.currentThread() + "  " + buf.toString(Charset.defaultCharset()));
                System.out.println("handler1");
                //将消息传给下一个handler，否则不会传输过去
                super.channelRead(ctx, msg);//等效于 ctx.fireChannelRead(msg);
            }
        };
        ChannelInboundHandlerAdapter handler2 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                System.out.println(Thread.currentThread() + "  " + buf.toString(Charset.defaultCharset()));
                System.out.println("handler2");
                //将消息传给下一个handler，否则不会传输过去
                super.channelRead(ctx, msg);//等效于 ctx.fireChannelRead(msg);
            }
        };
        ChannelInboundHandlerAdapter handler3 = new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                ByteBuf buf = (ByteBuf) msg;
                System.out.println(Thread.currentThread() + "  " + buf.toString(Charset.defaultCharset()));
                System.out.println("handler3");
                //将消息传给下一个handler，否则不会传输过去
                super.channelRead(ctx, msg);//等效于 ctx.fireChannelRead(msg);
            }
        };
        ChannelOutboundHandlerAdapter handler4 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                System.out.println("handler4");
                super.write(ctx, msg, promise);

            }
        };
        ChannelOutboundHandlerAdapter handler5 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                System.out.println("handler5");
                super.write(ctx, msg, promise);

            }
        };
        ChannelOutboundHandlerAdapter handler6 = new ChannelOutboundHandlerAdapter() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                System.out.println("handler6");
                super.write(ctx, msg, promise);

            }
        };
        //专门用于测试pipeline，不用创建seerver
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(handler1, handler2, handler3, handler4, handler5, handler6);

        embeddedChannel.writeInbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("inbound".getBytes(StandardCharsets.UTF_8)));
        embeddedChannel.writeOutbound(ByteBufAllocator.DEFAULT.buffer().writeBytes("outbound".getBytes(StandardCharsets.UTF_8)));

    }


}
