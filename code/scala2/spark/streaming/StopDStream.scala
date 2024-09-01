package scala2.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext, StreamingContextState}

/**
 * @author chao
 * @date 2023/1/23 22:50
 * @desc
 */
object StopDStream {

    def main(args: Array[String]): Unit = {
        func1()
    }

    // 优雅的关闭 SparkStreaming连接
    def func1(): Unit = {

        // nc -lp 9999
        val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
        val sc = new StreamingContext(sparkConf, Seconds(3))
        val lines: ReceiverInputDStream[String] = sc.socketTextStream("localhost", 9999)
        val wordToOne = lines.map((_, 1))
        wordToOne.print()

        sc.start()

        // 如果想要关闭采集器, 那么需要创建一个新的线程
        new Thread(
            new Runnable {
                override def run(): Unit = {
                    // 优雅的关闭, 计算节点不再接收新的数据, 而是将现有的数据处理完毕, 然后关闭
                    // 一般会用第三方程序来记录关闭状态: MySQL, Redis, Zookeeper, HDFS
                    while (true) {
                        if (true) {
                            val state: StreamingContextState = sc.getState()
                            if (state == StreamingContextState.ACTIVE) {
                                sc.stop(true, true)
                            }
                        }
                        Thread.sleep(5000)
                    }
                }
            }
        )

        sc.awaitTermination()

    }
}
