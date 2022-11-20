package h.code;

import java.util.Scanner;

public class J2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String content = sc.nextLine();
        String word = sc.nextLine();
        int contentlen=content.length();
        int wordlen=word.length();
        int count=0;
        for(int i = 0; i < contentlen-wordlen+1; i++){
            if(contains(content.substring(i,i+wordlen),word)){
                count++;
            }
        }
        System.out.println(count);
    }

    public static boolean contains(String a,String b){
        String a1=a;
        String a2=b;
        for (int i = 0; i < a1.length(); i++) {
            String temp=a2.replace(a1.charAt(i)+"","");
            if(temp.equals(a2)){
                return false;
            }else {
                a2=temp;
            }
        }
        return true;
    }
}
