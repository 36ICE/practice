package com.example.mykotlin

import java.util.*


//如果类有主构造函数，每个次构造函数都要，或直接或间接通过另一个次构造函数代理主构造函数。
// 在同一个类中代理另一个构造函数使用 this 关键字：
class Person {

    //getter 和 setter
    //属性声明的完整语法：
    //
    //var <propertyName>[: <PropertyType>] [= <property_initializer>]
    //    [<getter>]
    //    [<setter>]

    var lastName: String = "zhang"
        get() = field.uppercase(Locale.getDefault())   // 将变量赋值后转换为大写
        set

    var no: Int = 100
        get() = field                // 后端变量
        set(value) {
            if (value < 10) {       // 如果传入的值小于 10 返回该值
                field = value
            } else {
                field = -1         // 如果传入的值大于等于 10 返回 -1
            }
        }

    var heiht: Float = 145.4f
        private set
}


// 测试
fun main(args: Array<String>) {
    var person: Person = Person()

    person.lastName = "wang"

    println("lastName:${person.lastName}")

    person.no = 9
    println("no:${person.no}")

    person.no = 20
    println("no:${person.no}")

}