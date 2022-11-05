package hw.code;

import java.util.Scanner;

public class H4 {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s = in.nextLine();

        while (s.length() > 0) {
            String r = null;
            if (s.length() >= 8) {
                r = s.substring(0, 8);
                s = s.substring(8);
            } else {
                int x = 8;
                StringBuilder stringBuilder=new StringBuilder();

                stringBuilder.append(s);
                while (x - s.length() > 0) {
                    stringBuilder.append("0");
                    x--;
                }
                r=stringBuilder.toString();
                s = "";
            }
            System.out.println(r);
        }

    }
}
