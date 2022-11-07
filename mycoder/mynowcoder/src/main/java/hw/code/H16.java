package hw.code;

import java.util.*;

/**
 * 1000 5
 * 800 2 0
 * 400 5 1
 * 300 5 1
 * 400 3 0
 * 500 2 0
 */
public class H16 {

    public static void main(String[] args) throws CloneNotSupportedException {

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        //总钱数N
        int N = in.nextInt();
        //物品数m
        int m = in.nextInt();
        List<Goods> list = new ArrayList<>();

        //记录主附件编号
        List<Integer> indexList = new ArrayList<>();

        Map<Integer, List<Choose>> chooseMap = new HashMap<>();
        //读取
        for (int i = 0; i < m; i++) {
            int v = in.nextInt();
            int p = in.nextInt();
            int q = in.nextInt();
            if (q == 0) {
                //记录主附件
                indexList.add(i);
                ArrayList<Choose> chooses = new ArrayList<>();
                chooses.add(new Choose(i, v, p));
                chooseMap.put(i, chooses);
            }
            list.add(new Goods(v, p, q));
        }

        //排列组合附件
        for (int i = 0; i < list.size(); i++) {
            Goods goods = list.get(i);
            int q = goods.q;
            //附件
            if (q != 0) {
                //根据q拿到所有可以选择的Choose,然后加入当前附件后插入ChooseList
                int index = q - 1;
                List<Choose> toUpdateChoose = chooseMap.get(index);
                int sublen = toUpdateChoose.size();
                for (int j = 0; j < sublen; j++) {
                    Choose choose = toUpdateChoose.get(j);
                    //创建新的组合方式
                    Choose choose1 = choose.clone();
                    choose1.add(goods.v, goods.p);
                    toUpdateChoose.add(choose1);
                    chooseMap.put(index, toUpdateChoose);
                }
            }
        }

        //从chooseList中选择进行
        //行为可选择的物品，现在归纳为chooseMap长度，列为总钱数
        int r = chooseMap.size();
        int[][] dp = new int[r][N + 1];

        //初始化dp
        for (int i = 0; i < r; i++) {
            for (int j = 0; j <= N; j++) {
                dp[i][j] = 0;
            }
        }

        int i = 0;
        for (Map.Entry<Integer, List<Choose>> integerListEntry : chooseMap.entrySet()) {
            List<Choose> chooseList = integerListEntry.getValue();
            //总钱数
            for (int j = 1; j <= N; j++) {
                //递推公式
                //dp[i][j]
                dp[i][j] = getMaxSatisfy(chooseList, i, j, dp);
            }
            i++;
        }
        System.out.println(dp[r - 1][N]);
    }

    /**
     * 在money最大的满意度
     *
     * @param chooseList
     * @param i
     * @param j
     * @param dp
     * @return
     */
    public static int getMaxSatisfy(List<Choose> chooseList, int i, int j, int[][] dp) {

        if (i > 0) {
            dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
        } else {
            dp[i][j] = dp[i][j - 1];
        }
        for (Choose choose : chooseList) {
            //防止重复计算，如果要用此处choose，则必须将上一行的[j - choose.vsum] 再加上choose.satify
            if (i > 0 && choose.vsum <= j) {
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - choose.vsum] + choose.satify);
            } else if (i == 0 && choose.vsum <= j) {
                //第一行只需要考虑这一行
                dp[i][j] = Math.max(dp[i][j], choose.satify);
            }
        }
        return dp[i][j];
    }


}

/**
 * 将主附件的逻辑整理成一个Choose,不再考虑里面的细枝末节
 * 大问题不断拆解成小问题，二次拆解，和普通的01背包问题更深入，很具有教学意义。
 */
class Choose implements Cloneable {

    public Choose(int index, int v, int p) {
        this.index = index;
        this.v = v;
        this.p = p;
        satify = satify + p * v;
        vsum = vsum + v;
    }

    //主附件编号
    int index;

    int v;

    int p;

    //总价
    int vsum = 0;


    //价格
    List<Integer> vList = new ArrayList<>();
    //重要度
    List<Integer> pList = new ArrayList<>();


    int len = 0;
    //满意度
    int satify = 0;

    /**
     * 加入附件
     *
     * @param v
     * @param p
     */
    public void add(int v, int p) {
        vList.add(v);
        pList.add(p);
        len++;
        vsum = vsum + v;
        satify = satify + v * p;
    }

    @Override
    public Choose clone() throws CloneNotSupportedException {
        return (Choose) super.clone();
    }
}

class Goods {

    public Goods(int v, int p, int q) {
        this.v = v;
        this.p = p;
        this.q = q;
    }

    int v;
    int p;
    int q;
}