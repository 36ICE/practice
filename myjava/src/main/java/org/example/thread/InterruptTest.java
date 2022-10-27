package org.example.thread;

public class InterruptTest  extends Thread{
    class JThread extends Thread{

        @Override
        public void run() {


            while (true){
                System.out.println("===");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {


    }
}
