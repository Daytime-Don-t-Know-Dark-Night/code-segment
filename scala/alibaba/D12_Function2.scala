package alibaba

/**
 * @Author dingc
 * @Date 2022-08-29 22:40
 * @Description
 */
object D12_Function2 extends App {

    // 高阶函数将其他函数作为参数
    def apply(func: Int => Int, p: Int): Unit = {
        println(func(p))
    }

    // 普通函数
    def fn1(a: Int): Int = {
        a * a
    }

    apply(fn1, 10)
    
}
