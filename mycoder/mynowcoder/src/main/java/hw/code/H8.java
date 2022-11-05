package hw.code;


import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 合并表记录
 */
public class H8 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int count = in.nextInt();
        Map<Integer,Integer> map=new TreeMap<>();

        for (int i = 0; i < count; i++) {
            int k = in.nextInt();
            int v = in.nextInt();
            map.put(k,map.getOrDefault(k,0)+v);
        }
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            System.out.println(integerIntegerEntry.getKey()+" "+integerIntegerEntry.getValue());
        }

    }
}
