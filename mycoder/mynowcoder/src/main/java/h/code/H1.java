package h.code;

import java.util.Scanner;

public class H1 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.nextLine();
        int i = s.lastIndexOf(" ");
        System.out.println(s.substring(i + 1).length());
    }


    public void test1() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        int i = s.length() - 1;
        int t = 0;
        while (i >= 0 && s.charAt(i) != ' ') {
            t++;
            i--;
        }
        System.out.println(t);
    }
}
