package alibaba

/**
 * @Author dingc
 * @Date 2022-08-29 22:34
 * @Description
 */
object D11_DefaultValue extends App {

    // 定义一个默认参数的方法
    def add(a: Int = 3, b: Int = 4): Int = {
        a + b
    }

    val res1 = add()
    println(res1)

    val res2 = add(5)
    println(res2)

    val res3 = add(5, 7)
    println(res3)

    val res4 = add(b = 10)
    println(res4)

}
