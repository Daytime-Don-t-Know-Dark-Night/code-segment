package alibaba

/**
 * @Author dingc
 * @Date 2022-08-28 18:01
 * @Description
 */
object D6_Cycle extends App {

    // 定义一个数组
    var array = Array(1, 2, 3, 4, 5, 6)

    // 遍历
    for (arr <- array) {
        println(arr)
    }

    // 0 to 5 会生成一个范围集合 Range[0,1,2,3,4,5]
    val array_ = 0 to 5
    for (i <- 0 to 5) {
        println(i)
    }

    // 0 until 5 会生成一个范围集合 Range[0,1,2,3,4)
    for (i <- 0 until 5) {
        println(i)
    }

    // 在循环中增加条件
    for (i <- array if i % 2 == 0) {
        print("带条件: ")
        println(i)
    }

    // 双层循环
    for (i <- 1 to 3) {
        for (j <- 1 to 3) {
            if (i != j) {
                println(i * 10 + j + "")
            }
        }
    }

    // scala写法:
    for (i <- 1 to 3; j <- 1 to 3 if i != j) {
        println(i * 10 + j + "")
    }

    // yield 关键字将满足条件的值组合为一个新的数组
    val array2 = for (i <- array if i % 2 == 0) yield i
    for (i <- array2) {
        println(i)
    }

}
