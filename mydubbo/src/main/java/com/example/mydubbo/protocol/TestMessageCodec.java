package com.example.mydubbo.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class TestMessageCodec {

    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel=new EmbeddedChannel(
                //把日志往前移。我们可以看到先收到100字节，再接收到剩余的字节，放下面的话则只能看到一个完整的
                new LoggingHandler(),
                //线程不安全的，里面保存了状态信息，不能多线程使用，Netty中对handler加了@Sharable则标识可以共享使用，否则需要一个channel创建一个handler
                //所有的
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),

                new MessageCodec());

        //encode
        Message message = new LoginRequestMessage("zhangsan", "123465");
        channel.writeOutbound(message);

        //decode
        ByteBuf buf= ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buf);
//        channel.writeInbound(buf);


        //采用切片的方法进行测试粘包 半包问题  LengthFieldBasedFrameDecoder解决，发现读取数据不完整，就不会往后传了，他得等接受到完整的数据
//        ByteBuf s1 = buf.slice(0, 100);
//        channel.writeInbound(s1);


        ByteBuf s1 = buf.slice(0, 100);
        ByteBuf s2 = buf.slice(100, buf.readableBytes()-100);
        //由于出站入站会调用buf的release方法，因此此处需要调用retain方法
        s1.retain();//引用计数加1，这样调s2的时候不会报错
        channel.writeInbound(s1);
        channel.writeInbound(s2);
    }
}
