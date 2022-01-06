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
        func9(sparkContext)
    }

    // 从集合中创建RDD
    def func1(sparkContext: SparkContext): Unit = {
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
    def func2(sparkContext: SparkContext): Unit = {
        val fileRDD: RDD[String] = sparkContext.textFile("file:///D:/projects/parent/doc/test/rdd.txt")
        fileRDD.collect().foreach(println)
        sparkContext.stop()
    }

    // RDD转换算子 1. map
    def func3(sparkContext: SparkContext): Unit = {
        // 将处理的数据逐条映射转换, 可以是类型转换, 也可以是值转换
        val dataRDD: RDD[Int] = sparkContext.makeRDD(List(1, 2, 3, 4))
        val dataRDD1: RDD[Int] = dataRDD.map(num => {
            num * 2
        })
        val dateRDD2: RDD[String] = dataRDD1.map(num => {
            "" + num
        })
        dataRDD1.foreach(println)
    }

    // 2. mapPartitions
    // TODO 获取每个分区中的最大值
    def func4(sparkContext: SparkContext): Unit = {
        // 将待处理的数据以分区为单位发送到计算节点进行处理, 这里的处理是指可以进行任意处理, 哪怕只是过滤数据
        val dataRDD: RDD[Int] = sparkContext.makeRDD(List(1, 2, 3, 4))
        val dataRDD1: RDD[Int] = dataRDD.mapPartitions(data => {
            data.filter(_ == 2)
        })
        dataRDD1.foreach(println)
    }

    // map 和 mapPartitions 的区别:
    // 1.map算子是分区内一个数据一个数据的执行, 类似于串行操作, 而mapPartitions算子是以分区为单位进行批处理操作
    // 2.map算子主要是对数据源中的数据进行转换和改变, 并不会增加减少数据, 而mapPartitions算子需要传递一个迭代器, 返回一个迭代器, 可以对元素进行增减
    // 3.map算子类似于串行操作, 性能较低, mapPartitions类似于批处理, 性能较高, 但是消耗内存

    // 3.mapPartitionsWithIndex
    def func5(sparkContext: SparkContext): Unit = {
        // 将待处理的数据以分区为单位发送到计算节点进行处理, 任意处理, 可以过滤数据, 在处理的时候可以获取当前分区索引
        val dataRDD: RDD[Int] = sparkContext.makeRDD(List(1, 2, 3, 4))
        // val dataRDD1 = dataRDD.mapPartitionsWithIndex((index, data) => {
        //     data.map(index, _)
        // })
    }

    // 4.flatMap
    def func6(sparkContext: SparkContext): Unit = {
        // 将处理的数据进行扁平化后再进行映射处理, flatMap:扁平映射
        val dataRDD = sparkContext.makeRDD(List(List(1, 2), List(3, 4)), 1)
        val dataRDD1 = dataRDD.flatMap(list => {
            list
        })
        dataRDD1.foreach(println)
    }

    // 5.glom
    def func7(sparkContext: SparkContext): Unit = {
        // 将同一个分区的数据直接转换为相同类型的内存数组进行处理, 分区不变
        val dataRDD = sparkContext.makeRDD(List(1, 2, 3, 4), 1)
        val dataRDD1: RDD[Array[Int]] = dataRDD.glom()
        dataRDD1.foreach(println)
    }

    // 6.groupBy
    def func8(sparkContext: SparkContext): Unit = {
        // 将数据根据指定的规则进行分组, 分区默认不变, 但是数据会被打乱重新组合, 我们称这样的操作为shuffle, 极限情况下, 数据可能被分在同一个分区中
        // 一个组的数据在一个分区中, 但是并不是说一个分区中只能有一组数据
        val dataRDD = sparkContext.makeRDD(List(1, 2, 3, 4), 1)
        val dataRDD1 = dataRDD.groupBy(
            _ % 2
        )
        dataRDD1.foreach(println)
    }

    // 7.filter
    def func9(sparkContext: SparkContext): Unit = {
        // 将数据根据指定的规则进行筛选过滤, 符合规则的数据保留, 不符合规则的数据丢弃
        // 当数据进行筛选过滤后, 分区不变, 但是分区内的数据可能不均衡, 有可能出现数据倾斜
        val dataRDD = sparkContext.makeRDD(List(1, 2, 3, 4), 1)
        val dataRDD1 = dataRDD.filter(_ % 2 == 0)
        dataRDD1.foreach(println)
    }

    // 8.sample
    def func10(sparkContext: SparkContext): Unit = {

    }
}
