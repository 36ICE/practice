package org.example.util;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 集合底层无外乎两种结构
 * 1、数组
 * 2、对象
 * 3、数组+对象  HashMap
 */
public class MyArrayList {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");

        LinkedList<String> linkList = new LinkedList<>();
        linkList.add("a");

        System.out.println((Object) Object[].class);
        System.out.println(Object[].class.getComponentType());

        //java.lang.reflect.Array.newInstance(java.lang.Class<?>, int)
        //java.lang.Class.newInstance  废弃
        //使用  clazz.getDeclaredConstructor().newInstance()

        //创建对象,申请内存
//        T[] copy = ((Object)newType == (Object)Object[].class)
//                ? (T[]) new Object[newLength]
//                : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        //深度复制和浅度复制？
//        深复制与浅复制
//        当数组为一维数组，且元素为基本类型或String类型时，属于深复制，即原数组与新数组的元素不会相互影响
//        当数组为多维数组，或一维数组中的元素为引用类型时，属于浅复制，原数组与新数组的元素引用指向同一个对象
//                 这里说的影响，是两个数组复制后对应的元素，并不一定是下标对应
//                String的特殊是因为它的不可变性
//        System.arraycopy(original, 0, copy, 0,
//                Math.min(original.length, newLength));

        User[] users = new User[]{
                new User(1, "admin", "admin@qq.com"),
                new User(2, "maco", "maco@qq,com"),
                new User(3, "kitty", "kitty@qq,com")
        };//初始化对象数组
        User[] target = new User[users.length];//新建一个目标对象数组
        System.arraycopy(users, 0, target, 0, users.length);//实现复制
        System.out.println("源对象与目标对象的物理地址是否一样：" + (users[0] == target[0] ? "浅复制" : "深复制"));
        target[0].setEmail("admin@sina.com");
        System.out.println("修改目标对象的属性值后源对象users：");
        for (User user : users) {
            System.out.println(user);
        }

        t1();
        t2();
    }

    public static void t1() {
        int[] arr1 = {1, 2};
        int[] arr2 = {3, 4};
        int[] arr3 = {5, 6};
        int[] arr4 = {7, 8};

        int[][] src = new int[][]{arr1, arr2, arr3, arr4};
        int[][] dest = new int[4][];

        System.arraycopy(src, 0, dest, 0, 4);

        System.out.println("改变前");
        print("src = ", src);
        print("dest = ", dest);

        src[0][0] = 11111;

        System.out.println("改变后");
        print("src = ", src);
        print("dest = ", dest);
    }

    // 简单输出二维int数组的方法
    private static void print(String string, int[][] arr) {
        System.out.print(string);
        for (int[] a : arr) {
            for (int i : a) {
                System.out.print(i + " ");
            }
            System.out.print(",");
        }
        System.out.println();
    }

    public static void t2() {
        String str1 = "aa";
        String str2 = "bb";
        String str3 = "cc";
        String str4 = "dd";

        String[] src = {str1, str2, str3, str4};
        String[] dest = new String[4];

        System.arraycopy(src, 0, dest, 0, 4);

        System.out.println("改变前");
        print("src = ", src);
        print("dest = ", dest);

        src[0] = "abcd";

        System.out.println("改变后");
        print("src = ", src);
        print("dest = ", dest);
    }

    private static void print(String string, String[] arr) {
        System.out.print(string);
        for (String str : arr) {
            System.out.print(str + " ");
        }
        System.out.println();
    }
}

class User {
    private Integer id;
    private String username;
    private String email;

    //无参构造函数
    public User() {
    }

    //有参的构造函数
    public User(Integer id, String username, String email) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email
                + "]";
    }
}
