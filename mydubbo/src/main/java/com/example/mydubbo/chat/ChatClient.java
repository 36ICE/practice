package com.example.mydubbo.chat;

import com.example.mydubbo.chat.message.*;
import com.example.mydubbo.chat.protocol.MessageCodec;
import com.example.mydubbo.chat.protocol.MessageCodecSharable;
import com.example.mydubbo.chat.protocol.ProcotolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class ChatClient {

    public static void main(String[] args) {

        //换行 回车符
        final byte[] LINE = {13, 10};
        NioEventLoopGroup worker = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable messageCodecSharable = new MessageCodecSharable();
        MessageCodec messageCodec = new MessageCodec();

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

                    //用来判断是不是 读空闲时间过长，或者写空闲时间过长
                    //3s内如果没有向服务器写数据，会触发一个IdleState#WRITE_IDLE事件，是一个特殊事件，有专门的方法处理
                    ch.pipeline().addLast(new IdleStateHandler(0,3,0));
                    //使用ChannelDuplexHandler处理上面的特殊事件。 可以同时作为入站和出站处理器
                    ch.pipeline().addLast(new ChannelDuplexHandler(){
                        //用来触发特殊事件
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                            if(evt instanceof IdleStateEvent){
                                IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                                if (idleStateEvent.state()== IdleState.WRITER_IDLE) {
                                    log.debug("已经3s没有发消息了");

                                    //自动发送一个心跳包，防止服务器断开
                                    ctx.writeAndFlush(new PingMessage());
                                }
                            }
                        }
                    });




                    ch.pipeline().addLast("client handler",new ChannelInboundHandlerAdapter() {
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            new Thread(()->{
                                Scanner scanner=new Scanner(System.in);
                                System.out.println("请输入用户名:");
                                String username=scanner.nextLine();
                                System.out.println("请输入密码");
                                String password=scanner.nextLine();

                                //构造消息
                                LoginRequestMessage message=new LoginRequestMessage(username,password);
                                ctx.writeAndFlush(message);

                                try {
                                    WAIT_FOR_LOGIN.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(!LOGIN.get()){
                                    ctx.channel().close();
                                    return;
                                }
                                while (true){
                                    System.out.println("======================");
                                    System.out.println("菜单");
                                    System.out.println("send [username] [content]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gcreat [group name] [m1,m2,m3,...]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("======================");
                                    String command = scanner.nextLine();
                                    //假设所有的命令都是正确的
                                    String[] s = command.split(" ");
                                    switch (s[0]){
                                        case "send":
                                            ctx.writeAndFlush(new ChatRequestMessage(username,s[1],s[2]));
                                            break;
                                        case "gsend":
                                            ctx.writeAndFlush(new GroupChatRequestMessage(username,s[1],s[2]));
                                            break;
                                        case "gcreate":
                                            HashSet hashSet = new HashSet(Arrays.asList(s[2].split(",")));

                                            ctx.writeAndFlush(new GroupCreateRequestMessage(s[1],hashSet));
                                            break;
                                        case "gmembers":
                                            ctx.writeAndFlush(new GroupMembersRequestMessage(s[1]));
                                            break;
                                        case "gjoin":
                                            ctx.writeAndFlush(new GroupJoinRequestMessage(username,s[1]));
                                            break;
                                        case "gquit":
                                            ctx.writeAndFlush(new GroupQuitRequestMessage(username,s[1]));
                                            break;
                                        case "quit":
                                            ctx.channel().close();
                                            break;

                                    }
                                }
                            },"login").start();
                        }

                        /**
                         * 接受响应数据
                         * @param ctx
                         * @param msg
                         * @throws Exception
                         */
                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            log.debug("==={}",msg);
                            if(msg instanceof LoginResponseMessage){
                                LoginResponseMessage response = (LoginResponseMessage) msg;
                                if(response.isSuccess()){
                                    LOGIN.set(true);
                                }
                            }
                            WAIT_FOR_LOGIN.countDown();
                        }
                    });
                }
            });

            ChannelFuture channelFuture = bootstrap.connect("localhost", 9999).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {

            log.error("client error");
        } finally {
            worker.shutdownGracefully();
        }
    }
}
