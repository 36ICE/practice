package h.code;

import java.util.Scanner;


/**
 * 求int型正整数在内存中存储时1的个数
 * Integer.toBinaryString(num);
 * Integer.toOctalString(num);
 * Integer.toHexString(num);
 */
public class H15 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int num = in.nextInt();
        String s = Integer.toBinaryString(num);
//        Integer.toOctalString(num);
//        Integer.toHexString(num);
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                c++;
            }

        }
        System.out.println(c);
    }
}
