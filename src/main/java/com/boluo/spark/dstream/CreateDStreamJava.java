package com.boluo.spark.dstream;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * @author chao
 * @datetime 2023-08-30 17:49
 * @description
 */
public class CreateDStreamJava {

    public static void main(String[] args) {

        JavaStreamingContext ssc = new JavaStreamingContext(
                "local[2]",
                "JavaLocalNetworkWordCount",
                Durations.seconds(4),
                System.getenv("SPARK_HOME"),
                JavaStreamingContext.jarOfClass(CreateDStreamJava.class.getClass()));

        ssc.sparkContext().setLogLevel("ERROR");

        //创建一个RDD类型的queue
        LinkedList<JavaRDD<Integer>> queue = new LinkedList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            arrayList.add(i);
        }
        //ArrayList 转换成JavaRdd
        JavaRDD<Integer> javaRDD = ssc.sparkContext().parallelize(arrayList);

        //添加到 queue中
        queue.add(javaRDD);

        //创建QueueInputDStream 且接受数据和处理数据
        JavaDStream<Integer> integerJavaDStream = ssc.queueStream(queue);

        //mapToPair算子
        JavaPairDStream<Integer, Integer> integerIntegerJavaPairDStream = integerJavaDStream.mapToPair(new PairFunction<Integer, Integer, Integer>() {

            @Override
            public Tuple2<Integer, Integer> call(Integer i) throws Exception {

                return new Tuple2<Integer, Integer>(i % 10, 1);
            }
        });

        //reduceByKey算子
        JavaPairDStream<Integer, Integer> integerIntegerJavaPairDStream1 = integerIntegerJavaPairDStream.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        //将结果输出到控制台
        integerIntegerJavaPairDStream1.print();

        //显式的启动数据接收
        ssc.start();

        try {
            //来等待计算完成
            ssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
