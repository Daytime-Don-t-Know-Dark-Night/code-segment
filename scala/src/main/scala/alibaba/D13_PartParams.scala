package alibaba

/**
 * @Author dingc
 * @Date 2022-08-29 23:06
 * @Description
 */
object D13_PartParams extends App {

    // https://blog.csdn.net/wangshun_410/article/details/90759688

    // 定义一个求和函数
    def sum(a: Int, b: Int): Int = {
        a + b
    }

    // 调用sum函数的时候, 传入参数a=10, b为待定参数
    def sumWithTen: Int => Int = sum(10, _: Int)

    // 这里补充参数b
    println(sumWithTen(1))
    
}
