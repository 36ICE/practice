package com.example.mydubbo.chat.protocol;


import com.example.mydubbo.chat.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        //1.四个字节的魔术
        out.writeBytes(new byte[]{1,2,3,4});
        //2.一个字节的版本
        out.writeByte(1);
        //3. 一个字节表示序列化的方式 jdk 0 , json 1
        out.writeByte(0);
        //4. 一个字节表示指令类型
        out.writeByte(msg.getMessageTyp());
        //5. 4个字节的请求序号
        out.writeInt(msg.getSequenceId());
        //无意义，对齐填充
        out.writeByte(0xff);

        //6. 获取内容的字节数组   java对象转为字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();
        //7. 长度
        out.writeInt(bytes.length);
        //8.写入内容
        out.writeBytes(bytes);
    }

    /**
     * 怎么编码的就怎么解码
     * @param ctx
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int magicNum=in.readInt();
        byte version = in.readByte();
        byte serializerType =in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes=new byte[length];
        in.readBytes(bytes,0,length);
        if(serializerType==0){
            ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(bytes));
            Message message = (Message) ois.readObject();
            //将解析出来的消息放入out中，方便下一个handler使用
            out.add(message);
            log.debug("{}",message);
            log.debug("{},{},{},{},{},{}",magicNum,version,serializerType,messageType,sequenceId,length);
        }





    }
}
