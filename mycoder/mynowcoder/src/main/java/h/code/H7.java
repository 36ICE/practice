package h.code;

import java.math.BigDecimal;
import java.util.Scanner;


/**
 * https://www.runoob.com/w3cnote/java-the-different-float-double.html
 * double
 * float
 * 比如float类型是32位，是单精度浮点表示法：
 * 符号位（sign）占用1位，用来表示正负数。
 * 指数位（exponent）占用 8 位，用来表示指数。
 * 小数位（fraction）占用 23 位，用来表示小数，不足位数补 0。
 * 而 double 类型是 64 位，是双精度浮点表示法：
 * 符号位占用 1 位，指数位占用 11 位，小数位占用 52 位。
 * 到这里其实已经可以隐隐看出：
 * 指数位决定了大小范围，因为指数位能表示的数越大则能表示的数越大嘛！
 * 而小数位决定了计算精度，因为小数位能表示的数越大，则能计算的精度越大咯！
 *可能还不够明白，举例子吧：
 * float 的小数位只有 23 位，即二进制的 23 位，能表示的最大的十进制数为 2 的 23 次方，即 8388608，即十进制的 7 位，
 * 严格点，精度只能百分百保证十进制的 6 位运算。
 * double 的小数位有 52 位，对应十进制最大值为 4 503 599 627 370 496，这个数有 16 位，所以计算精度只能百分百保证十进制的 15 位运算。
 *
 * PS: 我们常见的科学计算器，比如高中时候用的，一般最大支持的运算位数就是 15 位，超过这个就不够准了。在实际编程中，也是用的 double 类型比较多，
 * 因为能够保证 15 位的运算。如果还需要更高精度的运算，则需要使用其他数据类型，比如 java 中的 BigDecimal 类型，能够支持更高精度的运算。
 *
 *指数位的偏移量与无符号表示
 * 需要注意的是指数可能是负数，也有可能是正数，即指数是有符号整数，而有符号整数的计算是比无符号整数麻烦的。所以为了减少不必要的麻烦，
 * 在实际存储指数的时候，需要把指数转换成无符号整数。那么怎么转换呢？
 *
 * 注意到 float 的指数部分是 8 位，则指数的取值范围是 -126 到 +127，为了消除负数带来的实际计算上的影响（比如比较大小，加减法等），
 * 可以在实际存储的时候，给指数做一个简单的映射，加上一个偏移量，比如float的指数偏移量为 127，这样就不会有负数出现了。
 */
public class H7 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        String  v = in.next();
        BigDecimal bigDecimal=new BigDecimal(v);
        bigDecimal = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigDecimal);
    }
}
