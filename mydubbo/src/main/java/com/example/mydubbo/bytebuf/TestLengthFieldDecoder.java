package com.example.mydubbo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestLengthFieldDecoder {

    public static void main(String[] args) {
        EmbeddedChannel channel =new EmbeddedChannel(
                //最后这个参数，需要剥离的字节，利于输出
                //adjust参数进行调节，增加版本号时
                new LengthFieldBasedFrameDecoder(1024,0,4,1,4),
                new LoggingHandler(LogLevel.DEBUG));

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        send(buf,"hello world");
        send(buf,"hi");
        channel.writeInbound(buf);

    }


    private static void send(ByteBuf buf,String content){
        byte[] bytes=content.getBytes();
        int length =bytes.length;
        buf.writeInt(length);
        //实际中，可记录版本号
        buf.writeByte(1);
        buf.writeBytes(bytes);
    }
}
