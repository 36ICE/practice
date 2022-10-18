package com.example.mydubbo.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TestRedis {

    /**
     * redis协议
     * set key value
     * 3 数组长度
     * $3
     * set
     * $4
     * name
     * $8
     * value
     */
    public static void main(String[] args) {

        //换行 回车符
        final byte[] LINE = {13, 10};
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ByteBuf buf = ctx.alloc().buffer();
                            buf.writeBytes("*3".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes((LINE));
                            buf.writeBytes("$3".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes((LINE));
                            buf.writeBytes("set".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes((LINE));
                            buf.writeBytes("$4".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes((LINE));
                            buf.writeBytes("name".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes((LINE));
                            buf.writeBytes("$8".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes((LINE));
                            buf.writeBytes("zhangsan".getBytes(StandardCharsets.UTF_8));
                            buf.writeBytes((LINE));
                            ctx.writeAndFlush(buf);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf buf = (ByteBuf) msg;
                            System.out.println(buf.toString(StandardCharsets.UTF_8));
                        }
                    });
                }
            });

            ChannelFuture channelFuture = bootstrap.connect("localhost", 6379).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {

            log.error("client error");
        } finally {
            worker.shutdownGracefully();
        }

    }


}
