package hw.code;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class H3 {


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int len = in.nextInt();
        int i = 0;

        Set<Integer> set = new TreeSet();
        while (in.hasNext() && i < len) { // 注意 while 处理多个 case
            int a = in.nextInt();
            set.add(a);
            i++;
        }
        set.forEach(System.out::println);


    }

    public void test1() {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int count = in.nextInt();    //随机数总数
            int[] data = new int[count];
            for (int i = 0; i < count; i++) //读入生成的随机数
                data[i] = in.nextInt();

            Arrays.sort(data);    //使用库函数排序
            System.out.println(data[0]);    //打印排序后的第一个数（必不重复）
            for (int i = 1; i < count; i++) { //打印其他数字，需与前面数字比较，不重复才能打印
                if (data[i] != data[i - 1]) System.out.println(data[i]);
            }
        }
    }

    public void test2() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();//下面一共n行
        int[] arr = new int[501];//设立一个500个空的数组
        int i = 1;
        while (i <= n) {//从第一行遍历到第n行
            int num = sc.nextInt();//第一行到第n行的输入
            if (arr[num] == 0) {
                arr[num] = num;
            }
            i++;
        }
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] != 0) {
                System.out.println(arr[j]);
            }
        }
    }
}
