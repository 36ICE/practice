package com.example.mykotlin

interface MyInterface {
    fun bar()    // 未实现
    fun foo() {  //已实现
        // 可选的方法体
        println("foo")
    }
}
class Child : MyInterface {
    override fun bar() {
        // 方法体
    }
}
interface MyInterface1 {
    fun bar()
    fun foo() {
        // 可选的方法体
        println("foo")
    }
}
class Child1 : MyInterface1 {
    override fun bar() {
        // 方法体
        println("bar")
    }
}
fun main(args: Array<String>) {
    val c =  Child()
    c.foo();
    c.bar();

}

//Kotlin 可以对一个类的属性和方法进行扩展，且不需要继承或使用 Decorator 模式。
//
//扩展是一种静态行为，对被扩展的类代码本身不会造成任何影响。


//扩展函数
//扩展函数可以在已有类中添加新的方法，不会对原类做修改，扩展函数定义形式：
//
//fun receiverType.functionName(params){
//    body
//}

class User(var name:String)

/**扩展函数**/
fun User.Print(){
    print("用户名 $name")
}

//fun main(arg:Array<String>){
//    var user = User("Runoob")
//    user.Print()
//}

//下面代码为 MutableList 添加一个swap 函数：

// 扩展函数 swap,调换不同位置的值
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]     //  this 对应该列表
    this[index1] = this[index2]
    this[index2] = tmp
}

//fun main(args: Array<String>) {
//
//    val l = mutableListOf(1, 2, 3)
//    // 位置 0 和 2 的值做了互换
//    l.swap(0, 2) // 'swap()' 函数内的 'this' 将指向 'l' 的值
//
//    println(l.toString())
//}

//扩展函数是静态解析的
//扩展函数是静态解析的，并不是接收者类型的虚拟成员，在调用扩展函数时，具体被调用的的是哪一个函数，由调用函数的的对象表达式来决定的，而不是动态的类型决定的:

//若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。
//
//class C {
//    fun foo() { println("成员函数") }
//}
//
//fun C.foo() { println("扩展函数") }
//
//fun main(arg:Array<String>){
//    var c = C()
//    c.foo()
//}

//扩展一个空对象
//在扩展函数内， 可以通过 this 来判断接收者是否为 NULL,这样，即使接收者为 NULL,也可以调用扩展函数。例如:
//
//fun Any?.toString(): String {
//    if (this == null) return "null"
//    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
//    // 解析为 Any 类的成员函数
//    return toString()
//}
//fun main(arg:Array<String>){
//    var t = null
//    println(t.toString())
//}

//伴生对象的扩展
//如果一个类定义有一个伴生对象 ，你也可以为伴生对象定义扩展函数和属性。
//
//伴生对象通过"类名."形式调用伴生对象，伴生对象声明的扩展函数，通过用类名限定符来调用

class MyClass {
    companion object { }  // 将被称为 "Companion"
}

fun MyClass.Companion.foo() {
    println("伴随对象的扩展函数")
}

val MyClass.Companion.no: Int
    get() = 10

//fun main(args: Array<String>) {
//    println("no:${MyClass.no}")
//    MyClass.foo()
//}