package hw.code;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class H2 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String s=in.nextLine().toUpperCase();
        Map<String,Integer> map=new HashMap();
        for (int i = 0; i < s.length(); i++) {
            String k=String.valueOf(s.charAt(i));
            int v=map.getOrDefault(k,0)+1;
            map.put(k,v);
        }
        String p=in.nextLine().toUpperCase();
        System.out.println(map.get(p));
    }
}
