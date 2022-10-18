package com.example.mydubbo.chat.handler;

import com.example.mydubbo.chat.message.GroupCreateRequestMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;


/**
 * 群聊创建消息处理器
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {

        String gname = msg.getGname();
        Set<String> set = msg.getSet();

    }
}
