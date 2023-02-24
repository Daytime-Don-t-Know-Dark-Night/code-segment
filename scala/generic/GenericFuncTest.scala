package generic

/**
 * @author chao
 * @date 2023/2/24 23:27
 * @desc
 */
object GenericFuncTest {

    // https://blog.csdn.net/qq_45765882/article/details/104335833

    def main(args: Array[String]): Unit = {
        val res1 = getMiddle1(Array(1, 2, 3, 4, 5))
        val res2 = getMiddle2(Array(1, 2, 3, 4, 5))
        val res3 = getMiddle2(Array("a", "b", "c", "d", "e"))

        println("res1: " + res1)
        println("res2: " + res2)
        println("res3: " + res3)
    }

    // 1. 不考虑泛型写一个方法
    def getMiddle1(array: Array[Int]): Int = {
        array(array.length / 2)
    }

    // 2. 加入泛型支持
    def getMiddle2[T](array: Array[T]): T = {
        array(array.length / 2)
    }

}
