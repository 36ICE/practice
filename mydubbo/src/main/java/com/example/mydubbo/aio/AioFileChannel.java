package com.example.mydubbo.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AioFileChannel {

    public static void main(String[] args) {
        try(AsynchronousFileChannel channel=AsynchronousFileChannel.open(Paths.get("data.txt"), StandardOpenOption.READ)) {
            //参数1 ByteBuffer结果
            //参数2 读取的起始位置
            //参数3 附件，一次读不完需要接着读
            //参数4 回调对象
            ByteBuffer buffer=ByteBuffer.allocateDirect(16);
            System.out.println("read start..");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    //成功调用 result读到的实际字节数  attachment 开始穿过来的attachment附件对象，一次读不完
                    attachment.flip();//读模式
                    //读取
                    while (attachment.hasRemaining()) {//是否还有剩余未读数据
                        byte b = attachment.get();//一次读一个字节
                        System.out.print((char) b);
                    }
                    System.out.println();
                    System.out.println(Thread.currentThread()+" read completed..");
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    //异常时调用
                    exc.printStackTrace();
                }
            });

            System.out.println("read end..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
