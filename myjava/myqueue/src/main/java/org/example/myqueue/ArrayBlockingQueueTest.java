package org.example.myqueue;

import java.util.concurrent.ArrayBlockingQueue;


/**
 * put -- take
 *
 *
 */
public class ArrayBlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<String> arrayBlockingQueue=new ArrayBlockingQueue(2);

        arrayBlockingQueue.add("aa");
        arrayBlockingQueue.offer("aa");

        arrayBlockingQueue.put("a");
        arrayBlockingQueue.put("b");
        arrayBlockingQueue.put("c");


    }
}
