package hw.code;

import java.util.*;

/**
 *HJ14 字符串排序
 *
 */
public class HJ14 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int num = in.nextInt();
        List<String> list = new ArrayList<>();

        String s = String.valueOf(num);
        for (int i = 0; i < num; i++) {
            list.add(in.next());
        }
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int c1 = 0, c2 = 0;
                while (c1 < o1.length() && c2 < o2.length()) {
                    if (o1.charAt(c1) < o2.charAt(c2)) {
                        return -1;
                    } else if (o1.charAt(c1) > o2.charAt(c2)) {
                        return 1;
                    } else {
                        c1++;
                        c2++;
                    }
                }
                if (o1.length() > c1) {
                    return 1;
                }
                if (o2.length() > c2) {
                    return -1;
                }
                return 0;
            }
        });
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }


    }
}
