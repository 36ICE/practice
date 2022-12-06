package com.example.demo;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DynamicCompiler {

    /**
     * 编译指定java源代码
     * 
     * @param javaSrc java源代码
     * @return 返回类的全限定名和编译后的class字节码字节数组的映射
     */
    public static Map<String, byte[]> compile(String javaSrc) {
        Pattern pattern = Pattern.compile("public\\s+class\\s+(\\w+)");
        Matcher matcher = pattern.matcher(javaSrc);
        if (matcher.find()) {
            return compile(matcher.group(1) + ".java", javaSrc);
        }
        return null;
    }

    /**
     * 用MemoryJavaFileManager替换JDK默认的StandardJavaFileManager，以便在编译器请求源码内容时，不是从文件读取，而是直接返回String；
     * 用MemoryOutputJavaFileObject替换JDK默认的SimpleJavaFileObject，以便在接收到编译器生成的byte[]内容时，不写入class文件，而是直接保存在内存中。
     * 最后，编译的结果放在Map<String, byte[]>中，Key是类名，对应的byte[]是class的二进制内容。
     * 
     * 为什么编译后不是一个byte[]呢？
     * 
     * 因为一个.java的源文件编译后可能有多个.class文件！只要包含了静态类、匿名类等，编译出的class肯定多于一个。
     * 
     * 如何加载编译后的class呢？
     * 
     * 加载class相对而言就容易多了，我们只需要创建一个ClassLoader，覆写findClass()方法：
     * 编译指定java源代码
     * 
     * @param javaName java文件名
     * @param javaSrc  java源码内容
     * @return 返回类的全限定名和编译后的class字节码字节数组的映射
     */
    public static Map<String, byte[]> compile(String javaName, String javaSrc) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource(javaName, javaSrc);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null,
                    Arrays.asList(javaFileObject));
            if (task.call()) {
                return manager.getClassBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
