package alibaba

/**
 * @Author dingc
 * @Date 2022-08-30 20:46
 * @Description
 */
object D15_PartialFunction extends App {

    // PartialFunction[A,B]
    // A代表参数类型, B代表返回值类型
    def fun1: PartialFunction[String, Int] = {
        case "one" => 1
        case "two" => 2
        case _ => -1
    }

    val res1 = fun1("one")
    val res2 = fun1("two")
    val res3 = fun1("three")

    println(res1)
    println(res2)
    println(res3)
}
