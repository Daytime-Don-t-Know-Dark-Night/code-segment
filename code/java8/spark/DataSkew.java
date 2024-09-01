package java8.spark;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;

import java.util.concurrent.TimeUnit;

import static org.apache.spark.sql.functions.*;

public class DataSkew {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder().master("local[*]").getOrCreate();
        func1();
        func2();
    }

    // 1. 局部打散
    public static void func1() {
        StructType scheme = new StructType()
                .add("时间", "string")
                .add("订单号", "string")
                .add("备注", "string");

        Row row1 = RowFactory.create("2022-01-01", "001", "备注1");
        Row row2 = RowFactory.create("2022-01-01", "002", "备注2");
        Row row3 = RowFactory.create("2022-01-02", "003", "备注3");
        Row row4 = RowFactory.create("2022-01-02", "004", "备注4");
        Row row5 = RowFactory.create("2022-01-03", "005", "备注5");
        Row row6 = RowFactory.create("2022-01-03", "006", "备注6");
        Row row7 = RowFactory.create("2022-01-03", "007", "备注7");
        Row row8 = RowFactory.create("2022-01-03", "008", "备注8");
        Row row9 = RowFactory.create("2022-01-03", "009", "备注9");
        Row row10 = RowFactory.create("2022-01-03", "010", "备注10");
        Row row11 = RowFactory.create("2022-01-03", "011", "备注11");
        Row row12 = RowFactory.create("2022-01-03", "012", "备注12");
        Row row13 = RowFactory.create("2022-01-03", "013", "备注13");
        Row row14 = RowFactory.create("2022-01-03", "014", "备注14");
        Row row15 = RowFactory.create("2022-01-03", "015", "备注15");
        Row row16 = RowFactory.create("2022-01-03", "016", "备注16");
        Row row17 = RowFactory.create("2022-01-04", "017", "备注17");
        Row row18 = RowFactory.create("2022-01-04", "018", "备注18");
        Row row19 = RowFactory.create("2022-01-05", "019", "备注19");

        Dataset<Row> ds = SparkSession.active().createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5, row6, row7, row8, row9,
                row10, row11, row12, row13, row14, row15, row16, row17, row18, row19), scheme);
        // ds.show(false);
        // +----------+------+------+
        // |时间      |订单号|备注  |
        // +----------+------+------+
        // |2022-01-01|001   |备注1 |
        // |2022-01-01|002   |备注2 |
        // |2022-01-02|003   |备注3 |
        // |2022-01-02|004   |备注4 |
        // |2022-01-03|005   |备注5 |
        // |2022-01-03|006   |备注6 |
        // |2022-01-03|007   |备注7 |
        // |2022-01-03|008   |备注8 |
        // |2022-01-03|009   |备注9 |
        // |2022-01-03|010   |备注10|
        // |2022-01-03|011   |备注11|
        // |2022-01-03|012   |备注12|
        // |2022-01-03|013   |备注13|
        // |2022-01-03|014   |备注14|
        // |2022-01-03|015   |备注15|
        // |2022-01-03|016   |备注16|
        // |2022-01-04|017   |备注17|
        // |2022-01-04|018   |备注18|
        // |2022-01-05|019   |备注19|
        // +----------+------+------+

        // 添加一列, 统计每一天的订单数
        ds.cache();
        ds.join(ds, "时间").show(false);
        ds.withColumn("ex", expr("count(0) over (partition by `时间`)")).show(false);
        Uninterruptibles.sleepUninterruptibly(50000, TimeUnit.SECONDS);
        // 分析Spark Jobs可知Spark对19条数据的全部处理过程:
        // Job Id = 3 的 Stage7中, 处理了14条数据, 其中有100个Task, task0处理了2条数据, task20处理了12条数据, 其余处理了0条数据
        // Job Id = 4 的 Stage9中, 处理了5条数据, 其中有75个Task, 其中有3个task分别处理了2,2,1条数据
        // 由此可知可见, Stage9中的task20处理的数据远大于其它任务所处理的数据, 可看为简单的数据倾斜

        // 处理数据倾斜主要的思路是对倾斜的数据重新打散, 再聚合出想要的结果
        // 打散形式分为两种, 局部打散和全部打散

        // 1.局部打散, 只把01-03这一天的日期打散即可
        // 这里只把01-03这一天的数据随机分成6份即可
        ds = ds.withColumn("tmp", expr("if(`时间`='2022-01-03',concat('id',int(rand()*6)),`时间`)"))
                .repartition(col("tmp"))
                .withColumn("tmpCount", expr("count(0) over (partition by tmp)"));
        // ds.show(false);
        // Uninterruptibles.sleepUninterruptibly(50000, TimeUnit.SECONDS);
        // 这里使用行动算子可以看出, 01-03日的数据已经被随机打散, 不再有task处理size=12的长数据, 而是改为多个task处理总和为12的短数据

        // 缩减数据, 只保留所需要的数据
        ds = ds.withColumn("rk", expr("row_number() over (partition by tmp order by `订单号`)"))
                .where("rk < 3");
        ds.withColumn("count", expr("sum(if(rk=1,tmpCount,0)) over (partition by `时间`)"))
                .show(false);
        Uninterruptibles.sleepUninterruptibly(50000, TimeUnit.SECONDS);
    }

    // 2. 全局打散
    public static void func2() {
        StructType scheme = new StructType()
                .add("时间", "string")
                .add("订单号", "string")
                .add("备注", "string");

        Row row1 = RowFactory.create("2022-01-01", "001", "备注1");
        Row row2 = RowFactory.create("2022-01-01", "002", "备注2");
        Row row3 = RowFactory.create("2022-01-02", "003", "备注3");
        Row row4 = RowFactory.create("2022-01-02", "004", "备注4");
        Row row5 = RowFactory.create("2022-01-03", "005", "备注5");
        Row row6 = RowFactory.create("2022-01-03", "006", "备注6");
        Row row7 = RowFactory.create("2022-01-03", "007", "备注7");
        Row row8 = RowFactory.create("2022-01-03", "008", "备注8");
        Row row9 = RowFactory.create("2022-01-03", "009", "备注9");
        Row row10 = RowFactory.create("2022-01-03", "010", "备注10");
        Row row11 = RowFactory.create("2022-01-03", "011", "备注11");
        Row row12 = RowFactory.create("2022-01-03", "012", "备注12");
        Row row13 = RowFactory.create("2022-01-03", "013", "备注13");
        Row row14 = RowFactory.create("2022-01-03", "014", "备注14");
        Row row15 = RowFactory.create("2022-01-03", "015", "备注15");
        Row row16 = RowFactory.create("2022-01-03", "016", "备注16");
        Row row17 = RowFactory.create("2022-01-04", "017", "备注17");
        Row row18 = RowFactory.create("2022-01-04", "018", "备注18");
        Row row19 = RowFactory.create("2022-01-05", "019", "备注19");

        Dataset<Row> ds = SparkSession.active().createDataFrame(ImmutableList.of(row1, row2, row3, row4, row5, row6, row7, row8, row9,
                row10, row11, row12, row13, row14, row15, row16, row17, row18, row19), scheme);

        // TODO 这里使用第二种处理方式, 全部打散
        ds = ds.withColumn("tmp", expr("int(rand)*6"))
                .repartition(col("时间"), col("tmp"));

    }

}
