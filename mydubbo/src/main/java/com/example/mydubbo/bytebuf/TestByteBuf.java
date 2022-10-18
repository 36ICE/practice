package com.example.mydubbo.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.nio.charset.StandardCharsets;

public class TestByteBuf {

    public static void main(String[] args) {
        ByteBuf buf= ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buf);
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sb.append("a");

        }
        buf.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println(buf);







    }
}
