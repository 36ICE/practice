package h.code;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 * 提取不重复的整数
 */
public class HJ9 {


    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);

        int num = scanner.nextInt();
        Set<String> iset=new HashSet<>();
        String s=String.valueOf(num);
        for (int i = s.length()-1; i >=0 ; i--) {
            //注意此处的一个类型 char和String类型不一样 "3" !=  '3'
            if(iset.contains(String.valueOf(s.charAt(i)))){
                continue;
            }
            System.out.print(s.charAt(i));
            iset.add(String.valueOf(s.charAt(i)));
        }

    }
}
