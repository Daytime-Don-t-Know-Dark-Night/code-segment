package spark.sharing;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chao
 * @datetime 2024-06-29 16:21
 * @description
 */
public class Demo02 {

    public static void main(String[] args) {

        String.join(",", Lists.newArrayList("1", "2"));

        try (SparkSession spark = SparkSession.builder().appName("SparkDemo02").master("local[*]").getOrCreate()) {
            JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
            List<Integer> data = Stream.iterate(1, n -> n + 1).limit(5).collect(Collectors.toList());
            data.forEach(System.out::println);

            JavaRDD<Integer> myRdd = sc.parallelize(data);
            System.out.println("total: " + myRdd.count());
            System.out.println("partition: " + myRdd.partitions());

            int max = myRdd.reduce(Integer::max);
            int min = myRdd.reduce(Integer::min);
            int sum = myRdd.reduce(Integer::sum);

            System.out.printf("max: %d, min: %d, sum: %d", max, min, sum);

            Uninterruptibles.sleepUninterruptibly(1, TimeUnit.DAYS);
        }
    }

}
