package cn.leetcode.problems;

public class C1455 {

    public static void main(String[] args) {
        System.out.println(isPrefixOfWord("i love eating burger", "burg"));
    }

    public static int isPrefixOfWord(String sentence, String searchWord) {
        String [] strs = sentence.split(" ");
        for (int i = 0; i < strs.length; i++) {
            if(strs[i].startsWith(searchWord)){
                return i;
            }
        }
        return -1;
    }
}
