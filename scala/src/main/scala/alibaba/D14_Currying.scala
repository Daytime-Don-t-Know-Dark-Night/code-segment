package alibaba

/**
 * @Author dingc
 * @Date 2022-08-30 20:28
 * @Description
 */
object D14_Currying extends App {

    // 柯里化: 将原来接受两个参数的函数变成新的接受一个参数的函数
    // 新的函数返回一个原有第二个参数为参数的函数

    // 定义一个求和函数
    def add(a: Int, b: Int) = a + b

    // 调用: add(1,2)
    // 将函数变形
    // def add(a: Int)(b: Int) = a + b

    def add(a: Int): Int => Int = {
        (b: Int) => a + b
    }

    var res = add(1)
    val sum = res(2)
    println(sum)
}
