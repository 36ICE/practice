package org.example.myqueue;

import java.util.Iterator;
import java.util.PriorityQueue;

public class PriorityQueueTest {

    public static void main(String[] args) {

        t1offerpoll();
        t2offerpoll();
        t3offerpoll();
        t1addpoll();
    }

    public static void t(){

    }
    public static void t1offerpoll(){
        System.out.println("--------t1offerpoll------");
        PriorityQueue<Integer> priorityQueue=new PriorityQueue();

        priorityQueue.offer(3);
        priorityQueue.offer(4);
        priorityQueue.offer(1);

        //不能这么使用，队列大小在减少
        for (int i = 0; i < priorityQueue.size(); i++) {
            System.out.println(priorityQueue.poll());
        }
    }
    public static void t2offerpoll(){
        System.out.println("--------t2offerpoll------");
        PriorityQueue<Integer> priorityQueue=new PriorityQueue();

        priorityQueue.offer(3);
        priorityQueue.offer(4);
        priorityQueue.offer(1);

        Iterator<Integer> iterator = priorityQueue.iterator();

        //无法保证顺序
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public static void t3offerpoll(){

        System.out.println("--------t3offerpoll------");
        PriorityQueue<Integer> priorityQueue=new PriorityQueue();

        priorityQueue.offer(3);
        priorityQueue.offer(4);
        priorityQueue.offer(1);

        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
    }
    public static void t1addpoll(){

        System.out.println("--------t1addpoll------");
        PriorityQueue<Integer> priorityQueue=new PriorityQueue();

        priorityQueue.add(3);
        priorityQueue.add(4);
        priorityQueue.add(1);

        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());
    }
    public static void t2addpeek(){

        System.out.println("--------t2addpeek------");
        PriorityQueue<Integer> priorityQueue=new PriorityQueue();

        priorityQueue.add(3);
        priorityQueue.add(4);
        priorityQueue.add(1);

        System.out.println(priorityQueue.peek());
        System.out.println(priorityQueue.peek());
        System.out.println(priorityQueue.peek());
    }
}
