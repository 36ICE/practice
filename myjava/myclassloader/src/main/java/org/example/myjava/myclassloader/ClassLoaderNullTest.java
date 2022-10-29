package org.example.myjava.myclassloader;


public class ClassLoaderNullTest {

    public static void main(String[] args) {
        ClassLoader platformClassLoader = ClassLoader.getPlatformClassLoader();
        platformClassLoader=null;

        //拿不到系统自带的三个类加载器，如果通过通过反射等方法拿到了，置为null会发生什么？？？？
//        ClassLoaders.platformClassLoader();

    }
}
