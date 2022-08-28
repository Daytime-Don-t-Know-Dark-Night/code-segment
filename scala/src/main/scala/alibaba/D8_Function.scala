package alibaba

/**
 * @Author dingc
 * @Date 2022-08-28 19:51
 * @Description
 */
object D8_Function extends App {

    // https://blog.csdn.net/wangshun_410/article/details/90759688

    // 方法可以转换为函数
    def m(a: Int, b: Int): Int = a * b

    val res = m(3, 4)
    println(res)

    val fm = m _
    val res2 = fm(2, 3)
    println(res2)

    // 函数定义一:
    // => 为函数标志
    val f1 = (x: Int) => x * 10
    val res_1 = f1(2)
    println(res_1)

    // 函数定义二:
    val f2: (Int, Int) => Int = (x, y) => x * y
    val res_2 = f2(9, 9)
    println(res_2)



}
