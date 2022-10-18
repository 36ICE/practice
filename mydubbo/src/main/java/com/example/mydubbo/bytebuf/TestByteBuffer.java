package com.example.mydubbo.bytebuf;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestByteBuffer {

    public static void main(String[] args) {

    }

    @Test
    public void test1(){
        //FileChannle
        //1.输入流输出流获取
        //2.RanDomAccessFile获取

        try (FileChannel channel = new FileInputStream("C:\\Users\\Administrator\\IdeaProjects\\practice\\data.txt").getChannel()){
            ByteBuffer buffer=ByteBuffer.allocate(10);//10个字节作为缓冲区
            //从channel读取数据，向buffer写入
            channel.read(buffer);
            //打印buffer的内容
            buffer.flip();//切换至buffer的读模式
            while (buffer.hasRemaining()){//是否还有剩余未读数据
                byte b = buffer.get();//一次读一个字节
                System.out.print((char)b);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        //FileChannle
        //1.输入流输出流获取
        //2.RanDomAccessFile获取

        try (FileChannel channel = new FileInputStream("C:\\Users\\Administrator\\IdeaProjects\\practice\\data.txt").getChannel()){
            ByteBuffer buffer=ByteBuffer.allocate(10);//10个字节作为缓冲区
            //从channel读取数据，向buffer写入
            while (true){
                int len=channel.read(buffer);
                System.out.println("读取字节len="+len);
                if(len==-1){
                    break;
                }
                //打印buffer的内容
                buffer.flip();//切换至buffer的读模式
                while (buffer.hasRemaining()){//是否还有剩余未读数据
                    byte b = buffer.get();//一次读一个字节
                    System.out.println((char)b);
                }
                buffer.clear();//切换至写模式
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
