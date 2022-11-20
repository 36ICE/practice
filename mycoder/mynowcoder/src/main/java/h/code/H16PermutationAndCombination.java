package h.code;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排列组合是组合学最基本的概念。
 * 所谓排列，就是指从给定个数的元素中取出指定个数的元素进行排序。
 * 组合则是指从给定个数的元素中仅仅取出指定个数的元素，不考虑排序。
 * C-Combination 组合数
 * A-Arrangement 排列数（在旧教材为P-Permutation）
 * 排列组合的中心问题是研究给定要求的排列和组合可能出现的情况总数。 排列组合与古典概率论关系密切。
 */
public class H16PermutationAndCombination {


    /**
     * 一般的代码实现都是利用递归的思想，或者更具体说，是使用深度优先搜索思想来解决
     * @param args
     */
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");


        List<Boolean> booleanList = new ArrayList<>();
        //初始化一个指定大小的list,在指定位置set存入元素，下标越界
        //List<String> resultList = new ArrayList<>(list.size());
        //而使用数组不会出现这样的问题！！！
        List<String> resultList = Arrays.asList(new String[list.size()]);
        for (int i = 0; i < list.size(); i++) {
            booleanList.add(false);

        }

        P(list, booleanList, resultList, 0);
        List<String> cresultList = Arrays.asList(new String[2]);

        C(list, 0, cresultList, 0);
    }


    /**
     * 所有排列数
     *
     * @param list
     * @param booleanList
     * @param resultList
     * @param index
     */
    public static void P(List<String> list, List<Boolean> booleanList, List<String> resultList, int index) {

        if (index >= list.size()) {
            System.out.println(resultList);
            return;
        }
        for (int i = 0; i < booleanList.size(); i++) {

            if (!booleanList.get(i)) {
                //Boolean 是不可变的，就像原始类型的所有包装器一样。
                booleanList.set(i, true);
                resultList.set(index, list.get(i));
                P(list, booleanList, resultList, index + 1);
                booleanList.set(i, false);
            }
        }
    }


    /**
     * 组合数要设置m，即resultList的大小
     *
     * @param list
     * @param dataIndex
     * @param resultList
     * @param resultIndex
     */
    public static void C(List<String> list, int dataIndex, List<String> resultList, int resultIndex) {

        int dataLen = list.size();
        //数量达到要求，进行输出
        if (resultIndex >= resultList.size()) {
            System.out.println(resultList);
            return;
        }
        for (int i = dataIndex; i < dataLen; i++) {
            resultList.set(resultIndex, list.get(i));
            C(list, i + 1, resultList, resultIndex + 1);
        }

    }

}
