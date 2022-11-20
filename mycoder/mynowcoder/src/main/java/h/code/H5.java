package h.code;

import java.util.Scanner;

/**
 * 进制转换
 */
public class H5 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.nextLine();
        int resut = 0;
        final int radix = 16;
        s = s.substring(2);
        for (int i = 0; i < s.length(); i++) {
//            System.out.println(Character.digit(s.charAt(i), 16));
            //在指定的基数返回字符ch的数值
            resut = radix * resut + Character.digit(s.charAt(i), radix);
        }
        System.out.println(resut);

    }


    public void t1() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.nextLine();

        System.out.println(Integer.parseInt(s.substring(2), 16));

    }
}