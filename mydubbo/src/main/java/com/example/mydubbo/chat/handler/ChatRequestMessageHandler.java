package com.example.mydubbo.chat.handler;

import com.example.mydubbo.chat.MemoryService;
import com.example.mydubbo.chat.message.ChatRequestMessage;
import com.example.mydubbo.chat.message.ChatResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 单聊消息处理器
 */
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {

        String to = msg.getTo();
        String from = msg.getFrom();
        Channel channel = MemoryService.username2Channel.get(to);
        if(channel!=null){
            channel.writeAndFlush(new ChatResponseMessage(from,msg.getContent()));
        }else {
            //不存在
            ctx.writeAndFlush(new ChatResponseMessage(false,"对方用户不存在或不在线"));
        }

    }
}
