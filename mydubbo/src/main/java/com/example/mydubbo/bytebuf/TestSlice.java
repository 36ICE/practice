package com.example.mydubbo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;

public class TestSlice {

    public static void main(String[] args) {

        ByteBuf buf= ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});

        System.out.println(buf);


        ByteBuf buf1=buf.slice(0,5);
        //做了slice操作之后，一般都要加retain，防止buf释放导致其他的切片不可用
        buf1.retain();
        ByteBuf buf2=buf.slice(5,5);
        buf2.retain();
        System.out.println(buf1.toString());
        System.out.println(buf2);

        buf1.setByte(0,'b');

        buf.release();
        System.out.println(buf);
        System.out.println(buf1);



        //
        ByteBuf buf3=ByteBufAllocator.DEFAULT.buffer();
        buf3.writeBytes(new byte[]{1,2,3,4});
        ByteBuf buf4=ByteBufAllocator.DEFAULT.buffer();
        buf4.writeBytes(new byte[]{5,6,7,8});

        CompositeByteBuf bufs=ByteBufAllocator.DEFAULT.compositeBuffer();

        //注意设置increaseWriteIndex为true,自动增加写指针
        bufs.addComponents(true,buf3,buf4);
        buf3.retain();
        buf4.retain();
        System.out.println(bufs);
        int len =bufs.readableBytes();
        for (int i = 0; i < len ; i++) {
            System.out.print(bufs.readByte());
        }
        System.out.println();
        System.out.println(bufs);
//        bufs.release();//调用此方法会自动释放子Bytebuf
        buf3.release();
        buf3.release();
        buf4.release();
        buf4.release();

        System.out.println(bufs);
        System.out.println(buf3);
        System.out.println(buf4);
    }
}
