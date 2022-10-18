package com.example.mydubbo.chat;


import com.example.mydubbo.chat.handler.RpcRequestMessageHandler;
import com.example.mydubbo.chat.protocol.MessageCodec;
import com.example.mydubbo.chat.protocol.MessageCodecSharable;
import com.example.mydubbo.chat.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 因为JDK9+之后都是模块化设计，为了Netty在JDK11下能够正确使用堆外内存，推荐在启动脚本中添加如下参数
 *
 * --add-opens java.base/jdk.internal.misc=ALL-UNNAMED  \
 * --illegal-access=warn \
 * -Dio.netty.tryReflectionSetAccessible=true \
 * 为了arthas的监控，也推荐添加如下参数
 *
 * --add-opens java.base/jdk.internal.perf=ALL-UNNAMED \
 * --add-exports java.base/jdk.internal.perf=ALL-UNNAMED
 *
 */
@Slf4j
public class RPCServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        MessageCodec messageCodec = new MessageCodec();
        RpcRequestMessageHandler rpcRequestMessageHandler=new RpcRequestMessageHandler();

        ServerBootstrap server = new ServerBootstrap()
                .group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new ProcotolFrameDecoder());
                        ch.pipeline().addLast(loggingHandler);
                        ch.pipeline().addLast(messageCodecSharable);
                        ch.pipeline().addLast(rpcRequestMessageHandler);
                    }
                });

        try {
            ChannelFuture future = server.bind(9999).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server start fail",e);
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
