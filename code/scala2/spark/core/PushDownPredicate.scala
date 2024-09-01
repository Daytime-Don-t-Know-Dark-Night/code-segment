package scala2.spark.core

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object PushDownPredicate {

    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val ds = spark.range(2)

        // 普通谓词下推
        ds.selectExpr("id as _id")
            .where("_id = 0")
            .explain(extended = true)

        /*

        == Parsed Logical Plan ==       (unresolved logical plan)
        'Filter ('_id = 0)
        +- Project [id#0L AS _id#2L]
           +- Range (0, 2, step=1, splits=Some(10))

        == Analyzed Logical Plan ==     (resolved logincal plan)
        _id: bigint
        Filter (_id#2L = cast(0 as bigint))
        +- Project [id#0L AS _id#2L]
           +- Range (0, 2, step=1, splits=Some(10))

        == Optimized Logical Plan ==
        Project [id#0L AS _id#2L]
        +- Filter (id#0L = 0)
           +- Range (0, 2, step=1, splits=Some(10))

        == Physical Plan ==
        *(1) Project [id#0L AS _id#2L]
        +- *(1) Filter (id#0L = 0)
           +- *(1) Range (0, 2, step=1, splits=10)

        */

        val ds2 = spark.range(10)
            .withColumn("id2", expr("id % 3"))

        // 当过滤字段在group by字段中时的谓词下推
        ds2.groupBy("id2")
            .agg(count("id"))
            .where("id2 = 0")
            .explain(true)

        /*

        == Parsed Logical Plan ==
        'Filter ('id2 = 0)
        +- Aggregate [id2#7L], [id2#7L, count(id#5L) AS count(id)#14L]
           +- Project [id#5L, (id#5L % cast(3 as bigint)) AS id2#7L]
              +- Range (0, 10, step=1, splits=Some(10))

        == Analyzed Logical Plan ==
        id2: bigint, count(id): bigint
        Filter (id2#7L = cast(0 as bigint))
        +- Aggregate [id2#7L], [id2#7L, count(id#5L) AS count(id)#14L]
           +- Project [id#5L, (id#5L % cast(3 as bigint)) AS id2#7L]
              +- Range (0, 10, step=1, splits=Some(10))

        == Optimized Logical Plan ==
        Aggregate [id2#7L], [id2#7L, count(1) AS count(id)#14L]
        +- Project [(id#5L % 3) AS id2#7L]
           +- Filter ((id#5L % 3) = 0)
              +- Range (0, 10, step=1, splits=Some(10))

        == Physical Plan ==
        *(2) HashAggregate(keys=[id2#7L], functions=[count(1)], output=[id2#7L, count(id)#14L])
        +- Exchange hashPartitioning(id2#7L, 200)
           +- *(1) HashAggregate(keys=[id2#7L], functions=[partial_count(1)], output=[id2#7L, count#25L])
              +- *(1) Project [(id#5L % 3) AS id2#7L]
                 +- *(1) Filter ((id#5L % 3) = 0)
                    +- *(1) Range (0, 10, step=1, splits=10)

        */

    }

}
