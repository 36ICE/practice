package com.example.mydubbo.chat;

import com.example.mydubbo.chat.handler.RpcResponseMessageHandler;
import com.example.mydubbo.chat.message.RpcRequestMessage;
import com.example.mydubbo.chat.protocol.MessageCodec;
import com.example.mydubbo.chat.protocol.MessageCodecSharable;
import com.example.mydubbo.chat.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class RPCClient {

    public static void main(String[] args) {

        //换行 回车符
        final byte[] LINE = {13, 10};
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        MessageCodec messageCodec = new MessageCodec();
        RpcResponseMessageHandler rpcResponseMessageHandler=new RpcResponseMessageHandler();

        CountDownLatch WAIT_FOR_LOGIN=new CountDownLatch(1);
        AtomicBoolean LOGIN=new AtomicBoolean(false);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast(new ProcotolFrameDecoder());
                    ch.pipeline().addLast(loggingHandler);
                    ch.pipeline().addLast(messageCodecSharable);
                    ch.pipeline().addLast(rpcResponseMessageHandler);


                }
            });

            ChannelFuture channelFuture = bootstrap.connect("localhost", 9999).sync();
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(new RpcRequestMessage(
                    1,
                    "com.example.mydubbo.chat.rpcservice.HelloService",
                    "sayHello",
                    String.class,
                    new Class[]{String.class},
                    new Object[]{"张三"}

            )).addListener(promise->{
              //增加对结果的验证
                if (!promise.isSuccess()) {
                    Throwable cause = promise.cause();
                    log.error("error",cause);
                }
            });


            channel.closeFuture().sync();

        } catch (InterruptedException e) {

            log.error("client error");
        } finally {
            worker.shutdownGracefully();
        }
    }
}
