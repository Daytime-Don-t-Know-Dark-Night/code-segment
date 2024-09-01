package scala2.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author chao
 * @date 2023/1/23 14:25
 * @desc
 */
object DStreamNoStatus {

    // nc -lp 9999
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
    val sc = new StreamingContext(sparkConf, Seconds(5))
    val lines: ReceiverInputDStream[String] = sc.socketTextStream("localhost", 9999)

    def main(args: Array[String]): Unit = {
        func1()
    }

    // 1. 无状态操作 transform
    def func1(): Unit = {

        // transform()方法可以将底层RDD获取之后进行操作
        // code: driver端
        val newDs: DStream[String] = lines.transform(
            rdd => {
                // code: driver端
                // SparkStream中, Spark会分批次收集RDD的所有数据, 所以这里的代码会周期性的执行
                rdd.map(
                    str => {
                        // code: executor端
                        str
                    }
                )
            }
        )

        // code: driver端
        val newDs1: DStream[String] = lines.map(
            data => {
                // code: executor端
                data
            }
        )

        sc.start()
        sc.awaitTermination()
    }

    // 2. 无状态操作 join
    def func2(): Unit = {

        val lines1: ReceiverInputDStream[String] = sc.socketTextStream("localhost", 8888)
        val lines2: ReceiverInputDStream[String] = sc.socketTextStream("localhost", 9999)

        val map1: DStream[(String, Int)] = lines1.map((_, 8))
        val map2: DStream[(String, Int)] = lines2.map((_, 9))

        val joinDs: DStream[(String, (Int, Int))] = map1.join(map2)
        joinDs.print()

        sc.start()
        sc.awaitTermination()
    }
}
