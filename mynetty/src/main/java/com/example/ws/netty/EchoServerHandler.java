package com.example.ws.netty;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
//com.example.ws.netty.EchoServerHandler channelActive
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("new client arrvie");
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello", CharsetUtil.UTF_8));  //激活后立即发送hello 消息
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        ByteBuf in = (ByteBuf) msg;
        ctx.writeAndFlush(in);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        ctx.close();
    }
}
