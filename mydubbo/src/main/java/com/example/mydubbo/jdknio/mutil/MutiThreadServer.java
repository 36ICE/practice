package com.example.mydubbo.jdknio.mutil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MutiThreadServer {


    public static void main(String[] args) throws IOException {

        Thread.currentThread().setName("boss");
        //已经不知道如何去爱了，天地之大，无一人可爱，不被一人所爱，大概这就是生活本来的模样吧。
        ServerSocketChannel ssc=ServerSocketChannel.open();
        //设置非阻塞
        ssc.configureBlocking(false);
        //绑定监听端口8888
        ssc.bind(new InetSocketAddress(8888));
        //定义Selector
        Selector bossSelector = Selector.open();
        ssc.register(bossSelector,SelectionKey.OP_ACCEPT,null);

        Worker[] workers=new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-"+i);
        }


        AtomicInteger index =new AtomicInteger(0);

        while (true){
            //初中2008   高中2011 大学2014 研究生2018 14年了，我从卑中来，时光自梦中去，它们从未停下脚本
            bossSelector.select();
            Set selectionKeys = bossSelector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //移除处理的事件，因此需要使用迭代器Iterator
                iterator.remove();
                if(selectionKey.isAcceptable()){
                    //处理accept事件
                    //创建读写通道和selector
                    ServerSocketChannel channel= (ServerSocketChannel) selectionKey.channel();
                    SocketChannel sc =channel.accept();
                    sc.configureBlocking(false);

                    //关联selector
                    System.out.println("before");
                    //初始化方法
                    workers[index.getAndIncrement() % workers.length].register(sc);
                    System.out.println("after");



//                    Selector workSelector=Selector.open();
//                    sc.register(workSelector, SelectionKey.OP_READ,null);
//                    workSelector.select();
//                    Set<SelectionKey> selectionKeys1 = workSelector.selectedKeys();
//
//                    Iterator<SelectionKey> iterator1 = selectionKeys1.iterator();
//
//                    while (iterator1.hasNext()){
//                        SelectionKey key = iterator1.next();
//                        iterator1.remove();
//                        if(key.isReadable()) {
//                            //处理读事件
//                        }
//                    }
                }

            }

        }


    }

    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String threadName;
        private volatile boolean start = false;//还未初始化

        private ConcurrentLinkedQueue<Runnable> queue=new ConcurrentLinkedQueue<>();
        public Worker(String threadName){

            this.threadName=threadName;
        }

        /**
         * 线程和selector初始化
         * @throws IOException
         */
        public void register(SocketChannel sc) throws IOException {
            if(!start){
                thread=new Thread(this,threadName);
                this.selector=Selector.open();
                thread.start();
                start=true;
            }

            queue.add(()->{
                try {
                    sc.register(selector,SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });

            selector.wakeup();//唤醒
        }


        @Override
        public void run() {

            //检测Channel的读写事件

            while (true){
                try {
                    selector.select(); //   会阻塞，wakeup唤醒
                    Runnable poll = queue.poll();
                    if(poll!=null){
                        poll.run();
                    }


                    Set<SelectionKey> selectionKeys = selector.selectedKeys();

                    Iterator<SelectionKey> iterator = selectionKeys.iterator();

                    while (iterator.hasNext()){

                        SelectionKey next = iterator.next();
                        if (next.isReadable()) {
                            SocketChannel channel = (SocketChannel) next.channel();
                            ByteBuffer dst=ByteBuffer.allocate(16);
                            //如何处理客户端异常断开，会触发read事件
                            try{
                                int read = channel.read(dst);
                                //断开分为强制断开（服务端需要使用catch捕获异常，并且cancel掉这个事件）与正常断开（调用close方法），无论是否正常异常断开，都会产生读事件，但是正常断开需要看read的正常返回结果
                                if(read==-1){
                                    //正常断开，看返回值是否-1
                                    System.out.println("正常断开："+next);
                                    next.cancel();
                                }else {
                                    dst.flip();
                                    while (dst.hasRemaining()) {//是否还有剩余未读数据
                                        byte b = dst.get();//一次读一个字节
                                        System.out.print((char) b);
                                    }
                                }
                            }catch (Exception e){
                                System.out.println("强制断开连接"+next);
                                next.cancel();//因为客户端断开了，需要将Key取消（从selector的key中删除）

                            }

                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}
