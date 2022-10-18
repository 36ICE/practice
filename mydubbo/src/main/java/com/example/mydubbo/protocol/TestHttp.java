package com.example.mydubbo.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

@Slf4j
public class TestHttp {

    public static void main(String[] args) {

        //换行 回车符
        final byte[] LINE = {13, 10};
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss,worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                    //http 的编解码器，codec包括编码和解码器，同时具有出站和入站的处理器
                    ch.pipeline().addLast(new HttpServerCodec());

                    //常规处理http消息
//                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                        @Override
//                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                            //查看解码后消息的类型
//                            log.debug("{}",msg.getClass());
//                            if(msg instanceof HttpRequest){
//
//                            }else if(msg instanceof HttpContent){
//
//                            }
//                        }
//                    });
                    //针对性处理handler
                    ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {

                            System.out.println(msg.uri());
                            System.out.println(msg.method());
                            DefaultFullHttpResponse defaultFullHttpResponse=new DefaultFullHttpResponse(msg.protocolVersion(), HttpResponseStatus.OK);
                            byte[] bytes = "<h1>hello world!</h1>".getBytes(StandardCharsets.UTF_8);
                            //设追响应长度，否则客户端会不知道内容有多长，会一直转圈圈
                            defaultFullHttpResponse.headers().setInt(CONTENT_LENGTH,bytes.length);
                            defaultFullHttpResponse.content().writeBytes(bytes);
                            //写回响应
                            ctx.writeAndFlush(defaultFullHttpResponse);
                        }
                    });

                }

            });

            ChannelFuture channelFuture = serverBootstrap.bind("localhost", 8080).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {

            log.error("client error");
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
