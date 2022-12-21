package alibaba

/**
 * @Author dingc
 * @Date 2022-08-28 19:41
 * @Description
 */
object D7_Operator extends App {

    // 定义一个sum方法, 两个参数, 返回值为Int类型
    def sum(a: Int, b: Int): Int = {
        a + b
    }

    // 调用
    val res = sum(1, 5)
    println(res)

    // 没有参数, 也没有返回值的方法
    def sayHello1 = println("Say 1")
    def sayHello2() = println("Say 2")

    // 如果方法没有(), 调用的时候也不能加()
    sayHello1
    // 可以省略(), 也可以不省略
    sayHello2()


}
