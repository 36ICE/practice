package com.example.mykotlin


//类的修饰符
//类的修饰符包括 classModifier 和_accessModifier_:
//
//classModifier: 类属性修饰符，标示类本身特性。
//
//abstract    // 抽象类
//final       // 类不可继承，默认属性
//enum        // 枚举类
//open        // 类可继承，类默认是final的
//annotation  // 注解类
//accessModifier: 访问权限修饰符
//
//private    // 仅在同一个文件中可见
//protected  // 同一个文件中或子类可见
//public     // 所有调用的地方都可见
//internal   // 同一个模块中可见
open class Base {
    open fun f() {}
}

abstract class Derived : Base() {
    override abstract fun f()
}
class Test {
    var v = "成员属性"

    fun setInterFace(test: TestInterFace) {
        test.test()
    }
}

/**
 * 定义接口
 */
interface TestInterFace {
    fun test()
}

fun main(args: Array<String>) {
    var test = Test()

    /**
     * 采用对象表达式来创建接口对象，即匿名内部类的实例。
     */
    test.setInterFace(object : TestInterFace {
        override fun test() {
            println("对象表达式创建匿名内部类的实例")
        }
    })
}