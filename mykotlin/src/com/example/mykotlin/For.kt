package com.example.mykotlin

class For() {

    fun f(): Unit {
        val items = listOf("apple", "banana", "kiwi")
        for (item in items) {
            println(item)
        }

        for (index in items.indices) {
            println("item at $index is ${items[index]}")
        }
    }


    fun w(): Unit {
        println("----while 使用-----")
        var x = 5
        while (x > 0) {
            println(x--)
        }
        println("----do...while 使用-----")
        var y = 5
        do {
            println(y--)
        } while (y > 0)
    }

    fun b(): Unit {
        for (i in 1..10) {
            if (i == 3) continue  // i 为 3 时跳过当前循环，继续下一次循环
            println(i)
            if (i > 5) break   // i 为 6 时 跳出循环
        }
    }
    //在 Kotlin 中任何表达式都可以用标签（label）来标记。
    // 标签的格式为标识符后跟 @ 符号，例如：abc@、fooBar@都是有效的标签。
    // 要为一个表达式加标签，我们只要在其前加标签即可。

//    标签限制的 break 跳转到刚好位于该标签指定的循环后面的执行点。 continue 继续标签指定的循环的下一次迭代。
    fun l(): Unit {
        loop@ for (i in 1..100) {
            for (j in 1..100) {
                if (i>2) break@loop
            }
        }
    }

}