package com.example.mydubbo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UnpooledDemo {

    public static void main(String[] args) {
        // 创建一个heapBuffer，是在堆内分配的
        ByteBuf heapBuf = Unpooled.buffer(5);
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
//            handleArray(array, offset, length);
        }

        if (heapBuf.hasArray()) {
            int length = heapBuf.readableBytes();
            byte[] array = new byte[length];
            heapBuf.getBytes(heapBuf.readerIndex(), array);
//            handleArray(array, 0, length);
            System.out.println(array);
        }
        // 创建一个directBuffer,是分配的堆外内存

        ByteBuf directBuf = Unpooled.directBuffer();
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
//            handleArray(array, 0, length);
        }

    }
}
