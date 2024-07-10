package spark.sharing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.util.CollectionAccumulator;
import org.apache.spark.util.LongAccumulator;
import scala.Option;

import java.util.ArrayList;
import java.util.List;

import static org.apache.spark.sql.functions.*;

/**
 * @author chao
 * @datetime 2024-07-07 19:43
 * @description
 */
public class Demo03_Accumulator {

    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {


        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        StructType scheme = new StructType()
                .add("id", "int")
                .add("name", "string")
                .add("age", "int")
                .add("info", "string");

        Row row1 = RowFactory.create(1, "dingc", 20, "{\"teacher\":\"Alice\",\"student\":[{\"name\":\"ming\",\"rank\":1},{\"name\":\"wang\",\"rank\":2}]}");
        Row row2 = RowFactory.create(2, "boluo", 30, "{\"teacher\":\"Alice\",\"student\":[{\"name\":\"hong\",\"rank\":1},{\"name\":\"bao\",\"rank\":2}]}");
        Row row3 = RowFactory.create(3, "qidai", 40, "{\"teacher\":\"Alice\",\"student\":[{\"name\":\"li\",\"rank\":1},{\"name\":\"ding\",\"rank\":2}]}");

        Dataset<Row> ds = spark.createDataFrame(ImmutableList.of(row1, row2, row3), scheme);
        ds.show(false);
        // +---+-----+---+---------------+-----------+---------+
        // |id |name |age|email          |phone      |image    |
        // +---+-----+---+---------------+-----------+---------+
        // |1  |dingc|20 |cding@state.com|16631086282|img/dingc|
        // |2  |boluo|30 |boluo@state.com|17731086282|img/boluo|
        // |3  |qidai|40 |qidai@state.com|18831086282|img/qidai|
        // +---+-----+---+---------------+-----------+---------+

        // 1. 累加器可以用于在程序运行过程中收集一些数据
        // 2. 使用累加器时需要注意只有Driver能够取到累加器的值，Task端进行的是累加操作。
        // 3. 为了保证准确性, 最好只使用一次 action 操作

        // List<String> names = new ArrayList<>();

        CollectionAccumulator<Object> firstDemo = new CollectionAccumulator<>();
        firstDemo.register(SparkSession.active().sparkContext(), Option.apply("firstDemo"), false);



        ds.foreach(i -> {
            String jsonInfoStr = i.<String>getAs("info");
            JsonNode jsonNode = mapper.readTree(jsonInfoStr);

            // 这里面每个数量是 2 , 总共循环了 3次
            Streams.stream(jsonNode.at("/student"))
                    .filter(rk -> rk.at("/rank").asInt() == 1)
                    .map(jn -> jn.at("/name").asText())

                    .forEach(firstDemo::add);

//                    .forEach(each -> {
//                        names.add(each);
//                        System.out.println("name added :" + each);
//                        System.out.println(names.toString());
//                    });

//                    .forEach(j -> {
//                        System.out.println(j.toString());
//                    });



        });

        // System.out.println("list count: " + names.size());
        System.out.println("count: " + firstDemo.value().size());
        System.out.println("count: " + firstDemo.value().toString());

    }


}
