package org.example.mylock;

import java.util.concurrent.Semaphore;

public class TestSemaphore {


    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore=new Semaphore(1);
        semaphore.acquire();
        semaphore.acquire();
        semaphore.release();
    }
}
