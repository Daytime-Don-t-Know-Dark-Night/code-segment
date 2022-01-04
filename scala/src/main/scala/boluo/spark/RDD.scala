package boluo.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Author dingc
 * @Date 2022/1/3 21:26
 */
object RDD {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf().setMaster("local[*]").setAppName("spark")
        val sparkContext = new SparkContext(sparkConf)


    }

    // 从集合中创建RDD
    def func1(sparkContext: SparkContext): Unit ={
        val rdd1 = sparkContext.parallelize(
            List(1, 2, 3, 4)
        )
        val rdd2 = sparkContext.makeRDD(
            List(1, 2, 3, 4)
        )
        rdd1.collect().foreach(println)
        rdd2.collect().foreach(println)
        sparkContext.stop()
    }

    // 从外部存储文件中创建RDD
    def func2(sparkContext: SparkContext): Unit ={
        val fileRDD: RDD[String] = sparkContext.textFile("file:///D:/projects/parent/doc/test/rdd.txt")
        fileRDD.collect()
    }

}
