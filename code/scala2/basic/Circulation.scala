package scala2.basic

import scala.util.control._

object Circulation {

    // TODO https://www.runoob.com/scala/scala-for-loop.html

    def main(args: Array[String]): Unit = {
        func71()
    }

    // 1. while
    def func1(): Unit = {
        var a = 10
        while (a < 20) {
            println("Value of a: " + a)
            a += 1
        }
    }


    // 2. do...while
    def func2(): Unit = {
        var b = 10
        do {
            println("Value of b: " + b)
            b += 1
        } while (b < 20)
    }


    // 3. for
    def func3(): Unit = {
        // i to j (包含j)
        var c = 0
        for (c <- 1 to 10) {
            println("value of c: " + c)
        }

        // i until j (不包含j)
        var d = 0
        for (d <- 1 until 10) {
            println("value of d: " + d)
        }
    }


    // 4. for循环设置区间
    def func4(): Unit = {
        // 在for循环中可以使用分号(;)来设置多个区间, 它将迭代给定区间所有的可能值, 笛卡尔积
        for (e <- 1 to 3; f <- 1 to 3) {
            println("value of e: " + e)
            println("value of f: " + f)
        }
    }


    // 5. for循环过滤
    def func5(): Unit = {
        for (x <- List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10) if x != 3; if x < 8) {
            println(x)
        }
    }


    // 6. for使用yield
    def func6(): Unit = {
        // 可以将for循环的返回值作为一个变量存储
        val retVal = for {x <- List(1, 2, 3, 4, 5, 6, 7, 8, 9) if x != 3; if x < 8} yield x

        // 输出返回值
        for (a <- retVal) {
            println("Value of a: " + a)
        }
    }


    // 7. loop循环实现循环中break
    def func7(): Unit = {
        val loop = new Breaks
        loop.breakable {
            for (x <- List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55)) {
                println(x)
                if (x == 13) {
                    loop.break()
                }
            }
        }
    }

    // 7. loop循环实现不同层级的break
    // 背景:
    // 以斐波那契数列形式的时间长短进行等待
    // 等待之后访问数据库, 数据库只会返回 'Success', 'Pending', 'Fail' 三种状态
    def func71(): Unit = {
        val feb = List(1, 1, 2, 3, 5, 8, 13, 21, 34, 55)
        val outLoop = new Breaks
        val innerLoop = new Breaks
        outLoop.breakable {
            innerLoop.breakable {
                for (x <- feb) {
                    println("等待时间: " + x)

                    // Success
                    // if (x == 13) {
                    //     outLoop.break()
                    // }

                    // Fail
                    if (x == 13) {
                        println("停止等待!!")
                        innerLoop.break()
                    }

                }
            }
            println("等待完成...记录数据...")
        }
    }


}
