package com.example.mydubbo.chat.handler;

import com.example.mydubbo.chat.MemoryService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 分正常断开与异常断开
 *
 */

@Slf4j
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        MemoryService memoryService = new MemoryService();
        //解绑
        memoryService.unbind(ctx.channel());

        log.debug("{}已经断开",ctx.channel());
        ctx.channel().close();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        MemoryService memoryService = new MemoryService();
        memoryService.unbind(ctx.channel());
        log.debug("{} 异常断开,异常是",ctx.channel(),cause);
        ctx.channel().close();
    }
}
