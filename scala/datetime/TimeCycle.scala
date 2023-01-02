package datetime

import java.time.LocalDateTime

/**
 * @author chao
 * @date 2022/12/30 14:30
 * @desc
 */
object TimeCycle {

    def main(args: Array[String]): Unit = {
        func1()
    }

    // 时间循环
    def func1(): Unit = {

        val startTime = LocalDateTime.of(2023, 1, 1, 0, 0)
        val endTime = LocalDateTime.of(2023, 1, 10, 0, 0)

        var k = 0
        var start = startTime
        while (start.compareTo(endTime) < 0) {
            println(start)
            k = k + 1
            start = startTime.plusDays(k)
        }

    }

}
