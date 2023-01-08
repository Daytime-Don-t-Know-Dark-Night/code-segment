package basic

import scala.collection.mutable.ArrayBuffer

/**
 * @author chao
 * @date 2023/1/8 21:13
 * @desc
 */
object ArrayTest {

    def main(args: Array[String]): Unit = {

        val array1 = new Array[Int](8)
        println(array1)
        println(array1.toBuffer)

        val array2 = Array("tom", "jack", "oliver")
        println(array2(1))

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
        array3.insert(0, -1, 0)
        array3.foreach(println)

        array3.remove(0, 1)
        array3.foreach(println)

    }

}
