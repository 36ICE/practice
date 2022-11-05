package hw.code;

import java.util.Scanner;


/**
 * 质数的性质
 * （1）质数p的约数只有两个：1和p。
 * （2）初等数学基本定理：任一大于1的自然数，要么本身是质数，要么可以分解为几个质数之积，且这种分解是唯一的。
 * （3）质数的个数是无限的。
 * （4）大于10的质数中，个位数只有1,3,7,9。
 *
 * 如果N (N>=2)没有小于等于根号N，大于1的约数，那么N必然是质数。
 * 同时可以使用 i*i<=N来简化 i<=sqrt(N)
 */
public class H6 {


    public static void main(String[] args) {

        /**
         * int 的大小是 4 个字节。它可以存储从 -2,147,483,648 到 2,147,483,647 的值
         * long 8 字节的原始数据类型,它可以存储从 -9,223,372,036,854,775,808 到 9,223,372,036,854,775,807 的值。
         */


        t1();
    }

    /**
     * 注意
     *
     */
    public static void t1() {
        Scanner sc = new Scanner(System.in);
        long num = sc.nextLong();
        long k = (long) Math.sqrt(num);// 优化,使得for循环提早结束.

        //如果N (N>=2)没有小于等于根号N，大于1的约数，那么N必然是质数。
        for (long i = 2; i <= k; i++) {
            while (num != i) {
                if (num % i == 0) {
                    System.out.print(i + " ");
                    num = num / i;
                } else {
                    break;
                }

            }
        }
        System.out.print(num);
    }

    /**
     * 我的错误解法
     *
     */
    public static void t2() {
        Scanner in = new Scanner(System.in);
        long num = in.nextLong();
        long k = (long) Math.sqrt(num);

        //1、是<=k还是 <k,边界不清晰
        for (int i = 2; i < k; i++) {
            //2、注意此处与下面t1解法的区别，一个是可以>=  一个是!=
            //在这个数本身是质数的时候会失败 64577
            while (num >= i) {
                if (num % i == 0) {
                    System.out.print(i + " ");
                    num /= i;
                } else {
                    break;
                }
            }


        }

    }
}
