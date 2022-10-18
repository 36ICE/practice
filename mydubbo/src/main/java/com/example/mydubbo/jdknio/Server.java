package com.example.mydubbo.jdknio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class Server {

    @Test
    public void test1() throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(16);
        //1创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//设置非阻塞模式
        //2.绑定监听的端口
        ssc.bind(new InetSocketAddress(8888));

        //3 连接集合

        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            SocketChannel sc = ssc.accept();//非阻塞，线程还会继续执行，如果没有建立连接，但sc是null
            if (sc != null) {
                sc.configureBlocking(false);//设置非阻塞模式
                channels.add(sc);

            }
            for (SocketChannel channel : channels) {
                try {
                    channel.read(buffer);//非阻塞，线程会继续执行，如果没有读到数据，read返回0
                } catch (Exception e) {
                    System.out.println(e);
//                    channels.remove(channel);
                }

                buffer.flip();

                while (buffer.hasRemaining()) {//是否还有剩余未读数据
                    byte b = buffer.get();//一次读一个字节
                    System.out.println((char) b);
                }

                buffer.clear();
            }
        }

    }
}
