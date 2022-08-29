package alibaba

/**
 * @Author dingc
 * @Date 2022-08-29 20:49
 * @Description
 */
object D9_Value extends App {

    def currentTime(): Long = {
        System.nanoTime()
    }

    def delayed(f: => Long): Unit = {
        println(s"time=${f}")
    }

    // 调用方法1
    delayed(currentTime())

    def delayed2(time: Long): Unit = {
        println(s"time=${time}")
    }

    // 调用方法2
    var time = currentTime()
    delayed2(time)

}
