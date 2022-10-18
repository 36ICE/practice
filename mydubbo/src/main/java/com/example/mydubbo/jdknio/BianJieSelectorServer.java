package com.example.mydubbo.jdknio;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class BianJieSelectorServer {

    //分为2种情况，长消息和短消息，会出现粘包和半包的问题
    public void split(ByteBuffer source) {

        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();

                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());

                }
                target.flip();
                while (target.hasRemaining()) {//是否还有剩余未读数据
                    byte b = target.get();//一次读一个字节
                    System.out.print((char) b);
                }
            }

        }
        source.compact();
    }


    @Test
    public void test1() throws IOException {

        Selector selector = Selector.open();
        //1创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);//设置非阻塞模式
        //2.绑定监听的端口
        //建立selector和channel的联系
        //SelectorKey就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        //管理serverSocketChannel的key
        //事件的类型 accept【会在有连接请求时触发】、connect【客户端连接建立后触发的事件】、read【可读事件】、write【可写事件】
        //sscKey只需要关注accept事件,ops=0,不关注任何事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        System.out.println("sscKey:"+sscKey);

        ssc.bind(new InetSocketAddress(8888));

        while (true) {

            //3.select方法 ,没有事件发生，线程阻塞，有事件线程才恢复运行
            selector.select();
            //4.处理事件，selectorKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                System.out.println("key:"+key);

                //处理key时，要从SelectedKeys集合中删除，否则下次处理就会有问题
                iterator.remove();


                //区分事件类型，因为多种事件都注册到同一个selector中
                if (key.isAcceptable()) {
                    //如果事件不处理则又会把事件加入事件集合，下次继续处理，
                    //或者不想处理可以使用kry.cancel()进行取消,总之不能不处理
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    //selector要配置非阻塞模式处理
                    sc.configureBlocking(false);//设置非阻塞模式
                    //我们需要将ByteBuffer关联到SelectionKey的附件attachment上
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);

                    System.out.println("sc:"+sc);


                } else if (key.isReadable()) {
                    try {
                        //如果是读事件,拿到触发事件的channel
                        SocketChannel channel = (SocketChannel) key.channel();

                        //长消息需要bytebuffer扩容，但如果将buffer提到外层作为全局变量，则会导致多个channel使用同一个buffer
                        //我们需要将ByteBuffer关联到SelectionKey的附件attachment上

                        //获取SelectionKey上关联的附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();

                        //key.attach()可以将扩容后的buffer关联到key

                        int read = channel.read(buffer);
                        if (read == -1) {
                            //正常断开，看返回值是否-1
                            System.out.println("正常断开：" + key);
                            key.cancel();
                        } else {
                            split(buffer);
                            //处理长消息，一次buffer读取不了，
//                            if (buffer.position() == buffer.limit()) {
//                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
//                                buffer.flip();
//                                newBuffer.put(buffer);//写入新buffer
//                                key.attach(newBuffer);//替换原有的buffer
//                            }

                        }
                    } catch (Exception e) {
                        System.out.println("e:"+e);
                        //捕获异常后，要把发生的事件进行处理,
                        //注意：不管客户端正常还是异常断开，都会产生读事件，
                        //1.客户端异常断开：因为客户端断开了，因此需要将key取消(从Selector 的Keys集合中真正删除key)
                        key.cancel();
                        //2.客户端正常断开，read的返回值是-1

                    }

                }
            }
        }

    }
}
