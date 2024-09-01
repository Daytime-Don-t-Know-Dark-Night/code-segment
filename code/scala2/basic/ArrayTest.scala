package scala2.basic

import scala.collection.mutable.ArrayBuffer

/**
 * @author chao
 * @date 2023/1/8 21:13
 * @desc
 */
object ArrayTest {

    // https://nummy-demo.readthedocs.io/zh_CN/latest/chapter/chapter04.html

    def main(args: Array[String]): Unit = {
        // func1()
        // func2()
        // func3()
        func4()
    }

    // 定长数组
    def func1(): Unit = {

        val array1 = new Array[Int](8)
        println(array1.mkString(","))
        println(array1.toBuffer)

        val array2 = Array("tom", "jack", "oliver")
        println(array2(1))
    }

    // 变长数组
    def func2(): Unit = {

        // 创建一个变长数组
        var array3 = ArrayBuffer[Int]()
        array3.foreach(println)

        // 追加多个元素
        array3 += (2, 3, 4, 5)
        array3.foreach(println)

        // 追加一个数组
        array3 ++= Array(6, 7)
        array3 ++= ArrayBuffer(8, 9)
        array3.foreach(println)

        // 在某个位置插入元素
        // array3.insert(0, -1, 0)
        array3.foreach(println)

        array3.remove(0, 1)
        array3.foreach(println)
    }

    // 数组求最大/最小值
    def func3(): Unit = {
        val array = Array(1, 2, 3, 4, 5, 6)
        val sum = array.sum
        val min = array.min
        val max = array.max
        println(s"sum: $sum, min: $min, max: $max")

        val sortArray = array.sortWith(_ < _)
        println(sortArray.mkString(", "))
    }

    // 创建二维数组
    def func4(): Unit = {
        val myMatrix = Array.ofDim[Int](3, 3)
        for (i <- 0 to 2) {
            for (j <- 0 to 2) {
                myMatrix(i)(j) = j
            }
        }
        for (i <- 0 to 2) {
            for (j <- 0 to 2) {
                print(" " + myMatrix(i)(j))
            }
            println()
        }
    }

}
