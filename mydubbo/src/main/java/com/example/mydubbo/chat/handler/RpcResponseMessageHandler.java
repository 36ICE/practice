package com.example.mydubbo.chat.handler;

import com.example.mydubbo.chat.message.RpcResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@ChannelHandler.Sharable
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {

    public static final Map<Integer, Promise<?>> rpcResponseMessageMap=new ConcurrentHashMap<>();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponseMessage msg) throws Exception {
        Promise promise = rpcResponseMessageMap.remove(msg.getSequenceId());
        if(promise!=null){
            promise.setSuccess(msg.getReturnValue());
        }else {
            promise.setFailure(msg.getExceptionValue());
        }
        log.debug("====={}",msg);
    }
}
