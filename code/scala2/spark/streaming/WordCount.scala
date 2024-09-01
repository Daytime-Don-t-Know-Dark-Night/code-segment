package scala2.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author chao
 * @date 2023/1/16 22:23
 * @desc
 */
object WordCount {

    // https://www.bilibili.com/video/BV11A411L7CK?p=187&spm_id_from=pageDriver&vd_source=e839d03e8fb3384d0ed571c1ffd80e49

    def main(args: Array[String]): Unit = {
        func1()
    }

    // 监听端口, 单词计数
    def func1(): Unit = {
        // 创建环境对象, 采集周期3秒
        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
        val sc = new StreamingContext(sparkConf, Seconds(3))

        // 获取端口数据, 这里使用netcat工具发送消息, nc -lp 9999
        val lines: ReceiverInputDStream[String] = sc.socketTextStream("localhost", 9999)

        // 通过空格符将消息转成DStream
        val words: DStream[String] = lines.flatMap(_.split(" "))
        val word2one: DStream[(String, Int)] = words.map((_, 1))
        val word2count: DStream[(String, Int)] = word2one.reduceByKey(_ + _)
        word2count.print()

        // 不能关闭环境对象, 保持长期执行
        // sc.stop()

        // 启动采集器, 等待采集器关闭
        sc.start()
        sc.awaitTermination()
    }

}
