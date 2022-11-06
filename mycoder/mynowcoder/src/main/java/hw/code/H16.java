package hw.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class H16 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        //总钱数N
        int N = in.nextInt();
        //物品数m
        int m = in.nextInt();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            list.add(in.next());
        }

    }
}
