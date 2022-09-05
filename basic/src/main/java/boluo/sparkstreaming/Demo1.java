package boluo.sparkstreaming;

/**
 * @Author dingc
 * @Date 2022-08-31 20:20
 * @Description
 */

import org.apache.spark.*;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.*;
import org.apache.spark.streaming.api.java.*;
import scala.Tuple2;

import java.util.Arrays;

public class Demo1 {

    public static void main(String[] args) {

        // 程序从TCP获取文本, 计算文本中包含的单词数
        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("WordCount");

        // 定义上下文
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));

        // 利用上下文, 创建DStream, 从TCP源获取流式数据
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);

        //
        JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(x.split(" ")).iterator());

        // 计算单词的个数
        JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));

        //


    }

}
