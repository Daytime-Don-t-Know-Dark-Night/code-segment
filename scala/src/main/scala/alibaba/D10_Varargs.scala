package alibaba

/**
 * @Author dingc
 * @Date 2022-08-29 22:26
 * @Description
 */
object D10_Varargs extends App {

    // 定义一个可变参数
    def varargsMethod(params: Int*): Unit = {
        for (i <- params) {
            println(i)
        }
    }

    // 调用
    varargsMethod(1, 2, 3, 5)

    // 传递数组时
    val array = Array(1, 3, 5, 7, 9)
    varargsMethod(array: _*)

}
