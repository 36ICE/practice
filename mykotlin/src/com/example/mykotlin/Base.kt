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
private fun foo() {} // 在 example.kt 内可见

public var bar: Int = 5 // 该属性随处可见

internal val baz = 6    // 相同模块内可见

//Kotlin 中所有类都继承该 Any 类，它是所有类的超类，对于没有超类型声明的类是默认超类：
//
//class Example // 从 Any 隐式继承

//子类有主构造函数
//如果子类有主构造函数， 则基类必须在主构造函数中立即初始化。

//open class Person(var name : String, var age : Int){// 基类
//
//}
//
//class Student(name : String, age : Int, var no : String, var score : Int) : Person(name, age) {
//
//}


//子类没有主构造函数
//如果子类没有主构造函数，则必须在每一个二级构造函数中用 super 关键字初始化基类，或者在代理另一个构造函数。初始化基类时，可以调用基类的不同构造方法。
//class Student : Person {
//
//    constructor(ctx: Context) : super(ctx) {
//    }
//
//    constructor(ctx: Context, attrs: AttributeSet) : super(ctx,attrs) {
//    }
//}

/**用户基类**/
//open class Person(name:String){
//    /**次级构造函数**/
//    constructor(name:String,age:Int):this(name){
//        //初始化
//        println("-------基类次级构造函数---------")
//    }
//}
//
///**子类继承 Person 类**/
//class Student:Person{
//
//    /**次级构造函数**/
//    constructor(name:String,age:Int,no:String,score:Int):super(name,age){
//        println("-------继承类次级构造函数---------")
//        println("学生名： ${name}")
//        println("年龄： ${age}")
//        println("学生号： ${no}")
//        println("成绩： ${score}")
//    }
//}




//在基类中，使用fun声明函数时，此函数默认为final修饰，不能被子类重写。如果允许子类重写该函数，那么就要手动添加 open 修饰它, 子类重写方法使用 override 关键词：

/**用户基类**/
//open class Person{
//    open fun study(){       // 允许子类重写
//        println("我毕业了")
//    }
//}
//
///**子类继承 Person 类**/
//class Student : Person() {
//
//    override fun study(){    // 重写方法
//        println("我在读大学")
//    }
//}

//如果有多个相同的方法（继承或者实现自其他类，如A、B类），则必须要重写该方法，使用super范型去选择性地调用父类的实现。
//
//open class A {
//    open fun f () { print("A") }
//    fun a() { print("a") }
//}
//
//interface B {
//    fun f() { print("B") } //接口的成员变量默认是 open 的
//    fun b() { print("b") }
//}
//
//class C() : A() , B{
//    override fun f() {
//        super<A>.f()//调用 A.f()
//        super<B>.f()//调用 B.f()
//    }
//}
//
//fun main(args: Array<String>) {
//    val c =  C()
//    c.f();
//
//}


//C 继承自 a() 或 b(), C 不仅可以从 A 或者 B 中继承函数，而且 C 可以继承 A()、B() 中共有的函数。此时该函数在中只有一个实现，
// 为了消除歧义，该函数必须调用A()和B()中该函数的实现，并提供自己的实现。


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