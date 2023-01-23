package spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @author chao
 * @date 2023/1/23 0:30
 * @desc
 */
object DStreamStatus {

    // nc -lp 9999
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")
    val sc = new StreamingContext(sparkConf, Seconds(3))
    val datas: ReceiverInputDStream[String] = sc.socketTextStream("localhost", 9999)

    def main(args: Array[String]): Unit = {
        func2()
    }

    def func1(): Unit = {

        // 无状态数据操作, 只对当前的采集周期内的数据进行处理, 例如输入12个a, 会分一次或多次分别统计 (a, 5) (a, 7)
        val wordToOne = datas.map((_, 1))
        val wordToCount = wordToOne.reduceByKey(_ + _)
        wordToCount.print()

        sc.start()
        sc.awaitTermination()
    }

    def func2(): Unit = {

        // 在使用有状态操作时, 需要设定检查点目录
        sc.checkpoint("checkpoint")
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

}
