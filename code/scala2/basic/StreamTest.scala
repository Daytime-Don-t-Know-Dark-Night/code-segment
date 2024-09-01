package scala2.basic

/**
 * @author chao
 * @date 2023/1/27 20:26
 * @desc
 */
object StreamTest {

    // Scala Stream 流用法

    def main(args: Array[String]): Unit = {
        func1()
    }

    // 创建一个流
    def func1(): Unit = {

        // Stream 基本操作
        val s1 = (1 to 10000).toStream

        val head = s1.head
        // head: 1

        val tail = s1.tail
        // Stream(2, ?)

        val ints = s1.take(2)
        // Stream(1, ?)

    }

    // map操作
    def func2(): Unit = {
        val s1 = (1 to 10000).toStream
        val ints = s1.map(_ * 3)
        // Stream(3, 6, 9, 12, 15, 18, 21, 24, 27, 30, ?)
    }

    // filter操作
    def func3(): Unit = {
        val s1 = (1 to 10000).toStream
        val ints = s1.filter(_ > 100)
        // Stream(101, ?)
    }


}
