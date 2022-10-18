package com.example.mydubbo.chat.handler;

import com.example.mydubbo.chat.message.RpcRequestMessage;
import com.example.mydubbo.chat.message.RpcResponseMessage;
import com.example.mydubbo.chat.rpcservice.HelloService;
import com.example.mydubbo.chat.rpcservice.ServiceFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Slf4j
@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequestMessage msg)  {
        Object result = null;
        RpcResponseMessage rpcResponseMessage=new RpcResponseMessage();
        rpcResponseMessage.setSequenceId(msg.getSequenceId());
        try {
            HelloService helloService = (HelloService)ServiceFactory.getService(Class.forName(msg.getInterfaceName()));
            Method method = helloService.getClass().getMethod(msg.getMethodName(), msg.getParameterType());
            result = method.invoke(helloService, msg.getParameterValue());


            rpcResponseMessage.setReturnValue(result);
        } catch (Exception e) {
            e.printStackTrace();
            rpcResponseMessage.setExceptionValue(e);
        }

        //发送响应
        ctx.writeAndFlush(rpcResponseMessage);
    }

    public static void main(String[] args) {

        RpcRequestMessage rpcRequestMessage=new RpcRequestMessage(
                1,
                "com.example.mydubbo.chat.rpcservice.HelloService",
                "sayHello",
                String.class,
                new Class[]{String.class},
                new Object[]{"张三"}
        );


        try {
            HelloService helloService = (HelloService)ServiceFactory.getService(Class.forName(rpcRequestMessage.getInterfaceName()));
            Method method = helloService.getClass().getMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterType());
            Object invoke = method.invoke(helloService, rpcRequestMessage.getParameterValue());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }



    }
}
