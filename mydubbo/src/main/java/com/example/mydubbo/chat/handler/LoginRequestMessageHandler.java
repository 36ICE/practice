package com.example.mydubbo.chat.handler;

import com.example.mydubbo.chat.MemoryService;
import com.example.mydubbo.chat.Service;
import com.example.mydubbo.chat.message.LoginRequestMessage;
import com.example.mydubbo.chat.message.LoginResponseMessage;
import com.example.mydubbo.chat.message.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *  登录消息处理器
 */
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {

        Service service = new MemoryService();
        boolean login = service.login(msg.getName(), msg.getPassword());
        Message message = null;
        if (login) {
            message = new LoginResponseMessage(true, "登录成功");
            MemoryService memoryService=new MemoryService();
            memoryService.bind(msg.getName(),ctx.channel());

        } else {
            message = new LoginResponseMessage(false, "登录失败");
        }
        ctx.writeAndFlush(message);
    }
}
