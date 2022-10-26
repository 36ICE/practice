package org.example.sentinel.ratelimiter;


/**
 * 漏桶算法实现
 */
public class LeakyBucketAlgorithm {
    private int capacity = 10;
    private long timeStamp = System.currentTimeMillis();

    public boolean getToken() {
        if (capacity > 0) {
            capacity--;
            return true;
        }
        long current = System.currentTimeMillis();
        if (current - timeStamp >= 100) {
            if ((current - timeStamp) / 100 >= 2) {
                // 假设100ms产生一个令牌
                capacity += (int)((current - timeStamp) / 100) - 1;
            }
            timeStamp = current;
            if (capacity > 10) capacity = 10;
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        LeakyBucketAlgorithm leakyBucketAlgorithm = new LeakyBucketAlgorithm();
        while (true) {
//            Thread.sleep(10);
            Thread.sleep(50);
            if (leakyBucketAlgorithm.getToken()) {
                System.out.println("获取令牌成功，可以执行业务逻辑了");
            } else {
                System.out.println("获取令牌失败，请稍后重试");
            }
        }
    }
}

