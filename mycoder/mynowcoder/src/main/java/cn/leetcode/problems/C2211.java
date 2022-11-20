package cn.leetcode.problems;

public class C2211 {

    public static void main(String[] args) {


    }

    public int countCollisions(String directions) {
        String s = directions;
        //被思路降维打击了
        s = s.replaceAll("^L+", ""); // 前缀向左的车不会发生碰撞
        s = new StringBuilder(s).reverse().toString().replaceAll("^R+", ""); // 后缀向右的车不会发生碰撞
        return s.length() - (int) s.chars().filter(c -> c == 'S').count(); // 剩下非停止的车必然会碰撞

    }

    public int countCollisions1(String directions) {


        int count = 0;
        //当前是否计算过
        boolean p = false;
        boolean f = false;
        int n = 0;
        for (int i = 0; i < directions.length() - 1; i++) {
            if (directions.charAt(i) == 'R' && 'L' == directions.charAt(i + 1)) {
                count += 2;
                p = true;
                f = true;
                n += 1;
            } else if (directions.charAt(i) == 'S' && 'L' == directions.charAt(i + 1) ||
                    directions.charAt(i) == 'R' && 'S' == directions.charAt(i + 1)) {
                count += 1;
                f = true;
                n += 1;
            } else if (directions.charAt(i) == 'L') {
                //当前未计算过 && 左侧有碰撞或者停留
                if (!p && f) {
                    count += 1;
                }
            } else if (directions.charAt(i) == 'S') {
                f = true;
                n += 1;
            } else {
                p = false;
            }


        }
        return count;
    }
}
