package com.example.mydubbo.jdknio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {

    @Test
    public void test1() throws IOException {
        ByteBuffer buffer=ByteBuffer.allocate(16);
        SocketChannel socketChannel=SocketChannel.open();

        boolean connect = socketChannel.connect(new InetSocketAddress("localhost",8888));
        buffer.put((byte) 'a');
//        socketChannel.write(buffer);
        socketChannel.write(Charset.defaultCharset().encode("abc"));


    }
}
