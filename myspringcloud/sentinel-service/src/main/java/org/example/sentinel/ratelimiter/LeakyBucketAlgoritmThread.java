package org.example.sentinel.ratelimiter;


/**
 *漏桶算法实现
 *
 */
public class LeakyBucketAlgoritmThread {
    private int capacity = 10;
    private long timeStamp = System.currentTimeMillis();


    public synchronized boolean getToken() {
        if (capacity > 0) {
            capacity--;
            return true;
        }
        long current = System.currentTimeMillis();
        if (current - timeStamp >= 100) {
            if ((current - timeStamp) / 100 >= 2) {
                capacity += (int)((current - timeStamp) / 100) - 1;
            }
            timeStamp = current;
            if (capacity > 10) capacity = 10;
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        LeakyBucketAlgoritmThread leakyBucketAlgorithmThread = new LeakyBucketAlgoritmThread();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
//                            Thread.sleep(900);
                            Thread.sleep(1000);
                            if (leakyBucketAlgorithmThread.getToken()) {
                                System.out.println(Thread.currentThread().getName()+" 获取令牌成功，可以执行业务逻辑了");
                            } else {
                                System.out.println(Thread.currentThread().getName()+" 获取令牌失败，请稍后重试");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}

