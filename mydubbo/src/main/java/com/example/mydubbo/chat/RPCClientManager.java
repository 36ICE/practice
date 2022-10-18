package com.example.mydubbo.chat;

import com.example.mydubbo.chat.handler.RpcResponseMessageHandler;
import com.example.mydubbo.chat.message.RpcRequestMessage;
import com.example.mydubbo.chat.protocol.MessageCodec;
import com.example.mydubbo.chat.protocol.MessageCodecSharable;
import com.example.mydubbo.chat.protocol.ProcotolFrameDecoder;
import com.example.mydubbo.chat.rpcservice.HelloService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class RPCClientManager {

    public static Channel channel=null;

    private static final Object lock=new Object();
    public static void main(String[] args) {

        //调用过程很繁琐，需要定义很多东西，我们需要定义代理类来帮我们完成这些操作
//        getChannel().writeAndFlush(new RpcRequestMessage(
//                1,
//                "com.example.mydubbo.chat.rpcservice.HelloService",
//                "sayHello",
//                String.class,
//                new Class[]{String.class},
//                new Object[]{"张三"}
//
//        )).addListener(promise->{
//            //增加对结果的验证
//            if (!promise.isSuccess()) {
//                Throwable cause = promise.cause();
//                log.error("error",cause);
//            }
//        });

        HelloService helloService = getProxyService(HelloService.class);
        System.out.println(helloService.sayHello("肖羽格"));

    }

    //创建代理类

    public static <T> T getProxyService(Class<T> clazz){

        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequestMessage rpcRequestMessage = new RpcRequestMessage(
                        Generator.nextId(),
                        clazz.getName(),
                        method.getName(),
                        method.getReturnType(),
                        method.getParameterTypes(),
                        args

                );
                getChannel().writeAndFlush(rpcRequestMessage);

                //客户端获取结果
                //准备一个Promise来接收结果                  //指定promise对象接收
                Promise promise=new DefaultPromise(channel.eventLoop());
                RpcResponseMessageHandler.rpcResponseMessageMap.put(rpcRequestMessage.getSequenceId(),promise);
                //等待promise结果。 RpcResponseMessageHandler接收到消息并填充
                promise.await();
                if(promise.isSuccess()){
                    //调用正常
                    return promise.getNow();
                }else {
                    //调用失败
                    throw new RuntimeException(promise.cause());
                }
            }
        });

        return (T) o;
    }

    public static Channel getChannel(){
        if(channel!=null){

            return channel;
        }
        synchronized (lock){
            if(channel!=null){
                return channel;
            }
            initChannel();
            return channel;
        }
    }
    /**
     * 初始化Channel
     */
    private static void initChannel() {
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
            channel = channelFuture.channel();


            channel.closeFuture().addListener(future -> {

                worker.shutdownGracefully();
            });

        } catch (InterruptedException e) {
            log.error("client error");
        }
    }
}
