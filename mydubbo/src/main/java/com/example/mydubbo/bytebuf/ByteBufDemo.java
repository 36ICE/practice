package com.example.mydubbo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.StandardCharsets;

public class ByteBufDemo {

    public static void main(String[] args) throws InterruptedException {
        //1、把消息内容通过Netty自带的缓存工具类转换成ByteBuf对象
        byte[] msg = "【有梦想的肥宅】".getBytes(StandardCharsets.UTF_8);
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.heapBuffer(msg.length);
        byteBuf.writeBytes(msg);

        //2、操作容量相关API
        System.out.println("==========A、开始操作容量相关的API==========");
        System.out.println("1、输出ByteBuf容量capacity:" + byteBuf.capacity());
        System.out.println("2、输出ByteBuf最大容量maxCapacity:" + byteBuf.maxCapacity());
        System.out.println("3、输出ByteBuf当前可读字节数readableBytes:" + byteBuf.readableBytes());
        System.out.println("4、输出ByteBuf当前是否可读isReadable:" + byteBuf.isReadable());
        System.out.println("5、输出ByteBuf当前可写字节数writableBytes:" + byteBuf.writableBytes());
        System.out.println("6、输出ByteBuf当前是否可写isWritable:" + byteBuf.isWritable());
        System.out.println("7、输出ByteBuf可写的最大字节数maxWritableBytes:" + byteBuf.maxWritableBytes());
        System.out.println();

        //3、操作读写指针相关API
        System.out.println("==========B、开始操作读写指针相关API==========");
        System.out.println("1、输出ByteBuf读指针readerIndex:" + byteBuf.readerIndex());
        System.out.println("2、输出ByteBuf写指针writerIndex:" + byteBuf.writerIndex());
        System.out.println("3、开始调用markReaderIndex()方法保存读指针:" + byteBuf.markReaderIndex());
        System.out.println("4、开始调用resetReaderIndex()方法恢复读指针【实现重复读】:" + byteBuf.resetReaderIndex());
        System.out.println("5、开始调用markWriterIndex()方法保存写指针:" + byteBuf.markWriterIndex());
        System.out.println("6、开始调用resetWriterIndex()方法恢复写指针【实现重复写】:" + byteBuf.resetWriterIndex());
        System.out.println();

        //4、操作读写相关API
        System.out.println("==========C、开始操作读写相关API==========");
        System.out.println("1、开始调用writeBytes()方法写入数据:" + byteBuf.writeBytes("开始写入消息".getBytes(StandardCharsets.UTF_8)));
        byte[] readBytes = new byte[byteBuf.readableBytes()];//新建一个容量为byteBuf可读长度的字节数组
        byteBuf.readBytes(readBytes);//从ByteBuf中读取数据
        System.out.println("2、开始调用readBytes()方法从ByteBuf中读出数据:" + new String(readBytes, StandardCharsets.UTF_8));
        //PS：Netty使用了堆外内存，而堆外内存不能被JVM的垃圾回收器回收，所以需要我们手动回收【手动释放内存】
        //PS：ByteBuf是通过引用计数的方式管理的，所以需要调用release()方法把引用计数设置为0，才能直接回收内存
        System.out.println("3、开始调用retain()方法增加引用计数:" + byteBuf.retain());
        System.out.println("4、开始多次调用release()方法直至内存释放:");
        System.out.println("    4.1 释放引用前：byteBuf的状态：" + byteBuf);
        System.out.println("    4.2 当前引用计数：" + byteBuf.refCnt());
        System.out.println("    4.3 开始释放引用计数：");
        int i = 1;
        while (byteBuf.refCnt() > 0) {
            System.out.println("        第" + i + "次释放引用次数结果：" + byteBuf.release());
            i++;
        }
        System.out.println("    4.4 释放引用后：byteBuf的状态：" + byteBuf);
        System.out.println();

        //5、操作复制相关API
        System.out.println("==========D、开始操作复制相关API==========");
        byte[] msgN = "快乐肥肥".getBytes(StandardCharsets.UTF_8);
        ByteBuf byteBufN = ByteBufAllocator.DEFAULT.heapBuffer(msgN.length);
        byteBufN.writeBytes(msgN);
        System.out.println("1、输出原对象:" + byteBufN);
        ByteBuf slice = byteBufN.slice();
        System.out.println("2、调用slice()方法复制对象:" + slice);
        System.out.println("    2.1 调用slice()方法有以下特点:");
        System.out.println("        2.1.1 最大容量为原byteBuf的可读容量【新对象的maxCapacity = 原对象的readableBytes()】");
        System.out.println("        2.1.2 底层内存和引用计数与原始的byteBuf共享，但读写指针不同");
        System.out.println("        2.1.3 不复制数据，只通过改变读写指针来改变读写行为");
        System.out.println("        2.1.4 不改变原byteBuf的引用计数，当原byteBuf调用release()方法时，slice()出来的对象也会被释放");
        ByteBuf duplicate = byteBufN.duplicate();
        System.out.println("3、调用duplicate()方法复制对象:" + duplicate);
        System.out.println("    3.1 调用duplicate()方法有以下特点:");
        System.out.println("        3.1.1 最大容量、数据内容、指针位置都和原来的byteBuf一样【整个新的byteBuf都和原byteBuf共享】");
        System.out.println("        3.1.2 底层内存和引用计数与原始的byteBuf共享，但读写指针不同");
        System.out.println("        3.1.3 不复制数据，只通过改变读写指针来改变读写行为");
        System.out.println("        3.1.4 不改变原byteBuf的引用计数，当原byteBuf调用release()方法时，slice()出来的对象也会被释放");
        ByteBuf copy = byteBufN.copy();
        System.out.println("4、调用copy()方法复制对象:" + copy);
        System.out.println("    4.1 调用copy()方法有以下特点:");
        System.out.println("        4.1.1 直接复制一个新的对象出来，包括指针位置、底层对应的数据等【往copy()方法复制出来的对象内写数据，不影响原来的byteBuf】");
        System.out.println("        4.1.2 当原byteBuf调用release()方法时，copy()出来的对象不会被释放");
        byteBufN.release();
        System.out.println("5、release()方法后，其余对象状态如下:");
        System.out.println("    5.1 原对象:" + byteBufN);
        System.out.println("    5.2 slice()方法复制的对象:" + slice);
        System.out.println("    5.3 duplicate()方法复制的对象:" + duplicate);
        System.out.println("    5.4 copy()方法复制的对象:" + copy);
    }
}
