package spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author chao
 * @date 2023/1/23 0:30
 * @desc
 */
object DStreamStatus {

    // nc -lp 9999, 在使用有状态操作时, 需要设定检查点目录
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
    val sc = new StreamingContext(sparkConf, Seconds(3))
    sc.checkpoint("checkpoint")
    val datas: ReceiverInputDStream[String] = sc.socketTextStream("localhost", 9999)

    def main(args: Array[String]): Unit = {
        func1()
    }

    def func0(): Unit = {

        // 无状态数据操作, 只对当前的采集周期内的数据进行处理, 例如输入12个a, 会分一次或多次分别统计 (a, 5) (a, 7)
        val wordToOne = datas.map((_, 1))
        val wordToCount = wordToOne.reduceByKey(_ + _)
        wordToCount.print()

        sc.start()
        sc.awaitTermination()
    }

    // 1. 有状态操作: updateStateByKey
    def func1(): Unit = {

        val wordToOne = datas.map((_, 1))

        // updateStateByKey(): 根据key对数据的状态进行更新
        val state = wordToOne.updateStateByKey((seq: Seq[Int], buffer: Option[Int]) => {
            val newCount = buffer.getOrElse(0) + seq.sum
            Option(newCount)
        })

        state.print()
        sc.start()
        sc.awaitTermination()
    }

    // 2. 有状态操作: window
    def func2(): Unit = {

        val wordToOne = datas.map((_, 1))

        // 窗口的范围应该是采集周期的整数倍
        // 不设置步长的话有可能会出现重复数据, 将步长设置为和窗口范围一样大, 就不会再出现重复数据
        // val windowDs: DStream[(String, Int)] = wordToOne.window(Seconds(6))
        val windowDs: DStream[(String, Int)] = wordToOne.window(Seconds(6), Seconds(6))
        val wordToCount = windowDs.reduceByKey(_+_)
        wordToCount.print()

        sc.start()
        sc.awaitTermination()
    }

}
