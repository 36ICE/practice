package com.example.ws.netty;

import io.netty.util.concurrent.DefaultEventExecutorChooserFactory;
import io.netty.util.concurrent.EventExecutor;

public class DefaultEventExecutorChooserFactorytest {

    public static void main(String[] args) {

        DefaultEventExecutorChooserFactory defaultEventExecutorChooserFactory =DefaultEventExecutorChooserFactory.INSTANCE;
        defaultEventExecutorChooserFactory.newChooser(new EventExecutor[]{});
        //内部源码 解析
        // private static boolean isPowerOfTwo(int val) {
        //        return (val & -val) == val;
        //    }
        //在计算机中，负数以其正值的补码形式表达
        //原码：一个整数，按照绝对值大小转换成的二进制数，称为原码。
        //反码：将二进制数按位取反，所得的新二进制数称为原二进制数的反码。
        //补码：反码加1称为补码

    }
}
