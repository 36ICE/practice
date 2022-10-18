package com.example.mydubbo.chat;


import com.example.mydubbo.chat.handler.ChatRequestMessageHandler;
import com.example.mydubbo.chat.handler.LoginRequestMessageHandler;
import com.example.mydubbo.chat.handler.QuitHandler;
import com.example.mydubbo.chat.protocol.MessageCodec;
import com.example.mydubbo.chat.protocol.MessageCodecSharable;
import com.example.mydubbo.chat.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
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
public class ChatServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        MessageCodec messageCodec = new MessageCodec();
        LoginRequestMessageHandler loginRequestMessageHandler = new LoginRequestMessageHandler();
        ChatRequestMessageHandler chatRequestMessageHandler = new ChatRequestMessageHandler();
        QuitHandler quitHandler = new QuitHandler();
        ServerBootstrap server = new ServerBootstrap()
                .group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new ProcotolFrameDecoder());
                        ch.pipeline().addLast(loggingHandler);
                        ch.pipeline().addLast(messageCodecSharable);

                        //用来判断是不是 读空闲时间过长，或者写空闲时间过长
                        //5s内如果没有收到channel的数据，会触发一个IdleState#READER——IDLE事件，是一个特殊事件，有专门的方法处理
                        ch.pipeline().addLast(new IdleStateHandler(5,0,0));
                        //使用ChannelDuplexHandler处理上面的特殊事件。 可以同时作为入站和出站处理器
                        ch.pipeline().addLast(new ChannelDuplexHandler(){
                            //用来触发特殊事件
                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                if(evt instanceof IdleStateEvent){
                                    IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                                    if (idleStateEvent.state()== IdleState.READER_IDLE) {
                                        log.debug("已经5s没有读到数据");
                                        ctx.channel().close();
                                    }
                                }
                            }
                        });



                        //SimpleChannelInboundHandler 针对特定类型进行处理，如LoginRequestMessage
                        ch.pipeline().addLast(loginRequestMessageHandler);
                        ch.pipeline().addLast(chatRequestMessageHandler);
                        ch.pipeline().addLast(quitHandler);
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
