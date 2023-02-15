package item

import org.apache.spark.sql.catalyst.plans.JoinType
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Row, RowFactory, SparkSession}

import scala.collection.JavaConversions

/**
 * @author chao
 * @date 2023/2/15 22:08
 * @desc
 */
object SqlToDataset {

    // SQL转DataSet算子写法

    def main(args: Array[String]): Unit = {
        func2()
    }

    // (SELECT后面)SQL子查询转DataSet
    def func1(): Unit = {

    }

    // (FROM后面)SQL子查询转DataSet
    def func2(): Unit = {

        // 使用FROM子查询, 查询各个部门平均工资的工资等级
        """
          |SELECT dept_id, salary_avg, level
          |FROM (
          |    -- 查询各个部门的平均工资
          |    SELECT dept_id, AVG(salary) salary_avg
          |    FROM employee e
          |    WHERE e.dept_id is not null
          |    GROUP BY dept_id
          |) a
          |INNER JOIN level_table b
          |ON a.salary_avg BETWEEN b.lowest_salary AND b.highest_salary;
          |""".stripMargin

        // SQL中涉及到两个表 employee 和 level_table, 这里我们使用ds1和ds2来表示

        val ds1 = getDs1
        // +-----------+-------+------+
        // |employee_id|dept_id|salary|
        // +-----------+-------+------+
        // |00A        |001    |3000  |
        // |00B        |001    |4000  |
        // |00C        |002    |5000  |
        // |00D        |002    |6000  |
        // |00E        |003    |7000  |
        // +-----------+-------+------+

        val ds2 = getDs2
        // +-----+-------------+--------------+
        // |level|lowest_salary|highest_salary|
        // +-----+-------------+--------------+
        // |A    |3000         |4000          |
        // |B    |5000         |6000          |
        // |C    |6000         |8000          |
        // +-----+-------------+--------------+

        var res = ds1.groupBy("dept_id")
            .agg(
                avg("salary").as("salary_avg")
            )

        res = res.as("a").join(
            ds2.as("b"),
            expr("a.salary_avg BETWEEN b.lowest_salary AND b.highest_salary"),
            "inner"
        ).select("dept_id", "salary_avg", "level")

        res.show(false)
        // +-------+----------+-----+
        // |dept_id|salary_avg|level|
        // +-------+----------+-----+
        // |003    |7000.0    |C    |
        // |001    |3500.0    |A    |
        // |002    |5500.0    |B    |
        // +-------+----------+-----+
    }

    def getDs1: Dataset[Row] = {
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema1 = new StructType()
            .add("employee_id", "string")
            .add("dept_id", "string")
            .add("salary", "int")

        val rows1 = Array(
            RowFactory.create("00A", "001", 3000: java.lang.Integer),
            RowFactory.create("00B", "001", 4000: java.lang.Integer),
            RowFactory.create("00C", "002", 5000: java.lang.Integer),
            RowFactory.create("00D", "002", 6000: java.lang.Integer),
            RowFactory.create("00E", "003", 7000: java.lang.Integer)
        )
        spark.createDataFrame(JavaConversions.seqAsJavaList(rows1), schema1)
    }

    def getDs2: Dataset[Row] = {
        val spark = SparkSession.builder().master("local[*]").getOrCreate()
        val schema2 = new StructType()
            .add("level", "string")
            .add("lowest_salary", "int")
            .add("highest_salary", "int")

        val rows2 = Array(
            RowFactory.create("A", 3000: java.lang.Integer, 4000: java.lang.Integer),
            RowFactory.create("B", 5000: java.lang.Integer, 6000: java.lang.Integer),
            RowFactory.create("C", 6000: java.lang.Integer, 8000: java.lang.Integer)
        )
        spark.createDataFrame(JavaConversions.seqAsJavaList(rows2), schema2)
    }

}
