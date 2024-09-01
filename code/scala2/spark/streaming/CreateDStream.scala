package scala2.spark.streaming

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{InputDStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable
import scala.util.Random


/**
 * @author chao
 * @date 2023/1/17 20:33
 * @desc
 */
object CreateDStream {

    // https://www.bilibili.com/video/BV11A411L7CK?p=189&vd_source=e839d03e8fb3384d0ed571c1ffd80e49

    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkStreaming")

    def main(args: Array[String]): Unit = {
        func2()
    }

    /** 使用 Queue[RDD]创建 DStream ********************************************************************************** */

    def func1(): Unit = {
        // 创建环境对象, 采集周期3秒
        val sc = new StreamingContext(sparkConf, Seconds(3))

        val rddQueue = new mutable.Queue[RDD[Int]]()
        val inputStream = sc.queueStream(rddQueue, oneAtATime = false)
        val mappedStream = inputStream.map((_, 1))
        val reducedStream = mappedStream.reduceByKey(_ + _)
        reducedStream.print()

        // 启动采集器, 等待采集器关闭
        sc.start()

        // 启动采集器之后, 往队列中添加RDD
        for (i <- 1 to 5) {
            rddQueue += sc.sparkContext.makeRDD(1 to 300, 10)
            Thread.sleep(2000)
        }

        sc.awaitTermination()
    }

    /** 自定义数据源 ************************************************************************************************** */

    def func2(): Unit = {

        val sc = new StreamingContext(sparkConf, Seconds(3))

        val messageDS: ReceiverInputDStream[String] = sc.receiverStream(new MyReceiver())
        messageDS.print()

        sc.start()
        sc.awaitTermination()
    }

    // 自定义数据采集器, 注意Receiver这个类的定义, 需要传递一个存储等级参数
    class MyReceiver extends Receiver[String](StorageLevel.MEMORY_ONLY) {

        private var flag = true

        override def onStart(): Unit = {
            new Thread(new Runnable {
                override def run(): Unit = {
                    while (flag) {
                        val message = "采集的数据为: " + new Random().nextInt(10).toShort
                        store(message)
                        Thread.sleep(500)
                    }
                }
            }).start()
        }

        override def onStop(): Unit = {
            flag = false
        }

    }

    /** kafka ******************************************************************************************************* */

    def func3(): Unit = {

        val sc = new StreamingContext(sparkConf, Seconds(3))
        val kafkaProp: Map[String, Object] = Map[String, Object](
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "linux1:9091,linux2:9092",
            ConsumerConfig.GROUP_ID_CONFIG -> "",
            "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
            "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer"
        )

        val kafkaDataDs: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](
            sc,
            LocationStrategies.PreferConsistent,
            ConsumerStrategies.Subscribe[String, String](Set(""), kafkaProp)
        )
        kafkaDataDs.map(_.value()).print()

        sc.start()
        sc.awaitTermination()

    }

    /** ************************************************************************************************************* */


}
