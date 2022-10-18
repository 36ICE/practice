package com.example.mydubbo.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {

    public static void main(String[] args) {
        //1.启动器 负责组装netty组件，启动服务器
        new ServerBootstrap()
                //2.BoosEventLoop ,WorkerEventLoop(selector,thread) ,group组
                .group(new NioEventLoopGroup())
                //3. 选择服务的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class) //OIO【】 BIO【】 NIO[比较通用的] 还有针对windows linux的实现的
                //boss负责处理连接，worker（child）负责处理读写的，决定了worker（child）能执行那些操作
                .childHandler(
                        //5.channel 代表与客户端进行数据读写的通道 Initializer 初始化，负责添加别的handler
                        new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //6.添加具体handler
                        ch.pipeline().addLast(new StringDecoder());//将ByteBuf类型转为字符串
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){//自定义的handler
                            @Override//读事件
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                //打印上一步解码后的字符串
//                                super.channelRead(ctx, msg);
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //7.绑定监听的端口
                .bind(8888);














    }
}
