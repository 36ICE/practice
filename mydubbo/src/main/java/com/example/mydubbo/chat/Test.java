package com.example.mydubbo.chat;

import com.example.mydubbo.chat.config.Config;
import com.example.mydubbo.chat.message.LoginRequestMessage;
import com.example.mydubbo.chat.message.Message;
import com.example.mydubbo.chat.protocol.MessageCodecSharable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Test {

    public static void main(String[] args) {
        MessageCodecSharable messageCodecSharable=new MessageCodecSharable();

        LoggingHandler loggingHandler=new LoggingHandler(LogLevel.DEBUG);
        EmbeddedChannel channel=new EmbeddedChannel(messageCodecSharable,loggingHandler);
        LoginRequestMessage loginRequestMessage=new LoginRequestMessage("zcd","123456");

        channel.writeInbound(loginRequestMessage);
        ByteBuf buf = messageToByteBuf(loginRequestMessage);
        channel.writeOutbound(buf);


    }

    public static ByteBuf messageToByteBuf(Message msg){
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        //1.四个字节的魔术
        out.writeBytes(new byte[]{1, 2, 3, 4});
        //2.一个字节的版本
        out.writeByte(1);
        //3. 一个字节表示序列化的方式 jdk 0 , json 1
        //ordinal 获取枚举顺序
        out.writeByte(Config.getSerializerAlgorithm().ordinal());
        //4. 一个字节表示指令类型
        out.writeByte(msg.getMessageTyp());
        //5. 4个字节的请求序号
        out.writeInt(msg.getSequenceId());
        //无意义，对齐填充
        out.writeByte(0xff);

        //6. 获取内容的字节数组   java对象转为字节数组
        //根据配置调用序列化算法
        byte[] bytes =Config.getSerializerAlgorithm().serialize(msg);
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(bos);
//        oos.writeObject(msg);
//        byte[] bytes = bos.toByteArray();
        //7. 长度
        out.writeInt(bytes.length);
        //8.写入内容
        out.writeBytes(bytes);
        return out;
    }
}
