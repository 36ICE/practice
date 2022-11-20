package cn.leetcode.problems;

import java.util.Arrays;
import java.util.List;

public class C1431 {


    public static void main(String[] args) {

    }

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {

        int max = Integer.MIN_VALUE;

        for (int i = 0; i < candies.length; i++) {
            if (candies[i] > max) {
                max = candies[i];
            }
        }
        Boolean[] booleans = new Boolean[candies.length];
        Arrays.fill(booleans, false);
        List<Boolean> list = Arrays.asList(booleans);
        for (int i = 0; i < candies.length; i++) {
            if (candies[i] + extraCandies >= max) {
                list.set(i, true);
            }
        }
        return list;
    }


}
