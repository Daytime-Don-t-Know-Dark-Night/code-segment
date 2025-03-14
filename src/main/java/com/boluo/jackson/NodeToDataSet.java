package com.boluo.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author chao
 * @date 2022/11/16 22:50
 * @desc
 */
public class NodeToDataSet {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final SparkSession spark = SparkSession
            .builder().master("local[*]")
            .config("spark.driver.host", "localhost")
            .getOrCreate();

    public static void main(String[] args) throws IOException {

        String path1 = "C:\\Users\\chao\\IdeaProjects\\dingx\\doc\\json\\1.json";
        String path2 = "C:\\Users\\chao\\IdeaProjects\\dingx\\doc\\json\\2.json";
        String path3 = "C:\\Users\\chao\\IdeaProjects\\dingx\\doc\\json\\3.json";
        ObjectNode node1 = (ObjectNode) mapper.readTree(new File(path1));
        ObjectNode node2 = (ObjectNode) mapper.readTree(new File(path2));
        ObjectNode node3 = (ObjectNode) mapper.readTree(new File(path3));

        List<ObjectNode> objectNodes = Lists.newArrayList(node1, node2, node3);
        Dataset<ObjectNode> objectNodeDs = spark.createDataset(objectNodes, Encoders.kryo(ObjectNode.class));
        objectNodeDs.show(false);

        Dataset<String> stringDs = objectNodeDs.map((MapFunction<ObjectNode, String>) mapper::writeValueAsString, Encoders.STRING());
        stringDs.show(false);

        // 如果我将2.json的slave值改为2或者'asd'
        // slave: string (nullable = true)
        // +---+----+-----+---------------+-------------------------------------------------------------------------+
        // |HP |ID  |Name |email          |slave                                                                    |
        // +---+----+-----+---------------+-------------------------------------------------------------------------+
        // |100|S001|boluo|boluo@state.com|[{"ID":"M001","Name":"zhq","HP":120},{"ID":"M002","Name":"lqy","HP":175}]|
        // |100|S002|qidai|qidai@state.com|2                                                                        |
        // |100|S003|dingc|cding@state.com|[{"ID":"M001","Name":"zhq","HP":120},{"ID":"M002","Name":"lqy","HP":175}]|
        // +---+----+-----+---------------+-------------------------------------------------------------------------+
        Dataset<Row> jsonDs = spark.read().json(stringDs);
        jsonDs.printSchema();
        jsonDs.show(false);

        StructType innerSchema = new StructType()
                .add("ID", DataTypes.StringType)
                .add("Name", DataTypes.StringType)
                .add("HP", DataTypes.StringType);

        StructType schema = new StructType()
                .add("ID", DataTypes.StringType)
                .add("Name", DataTypes.StringType)
                .add("HP", DataTypes.StringType)
                .add("email", DataTypes.StringType)
                .add("slave", ArrayType.apply(innerSchema));

        // 如果我将2.json的slave值改为2或者'asd'
        // 类型不变, 会按照指定的来
        // +----+-----+----+---------------+------------------------------------+
        // |ID  |Name |HP  |email          |slave                               |
        // +----+-----+----+---------------+------------------------------------+
        // |S001|boluo|100 |boluo@state.com|[[M001, zhq, 120], [M002, lqy, 175]]|
        // |null|null |null|null           |null                                |
        // |S003|dingc|100 |cding@state.com|[[M001, zhq, 120], [M002, lqy, 175]]|
        // +----+-----+----+---------------+------------------------------------+
        Dataset<Row> dsJson = spark.read().schema(schema).json(stringDs);
        dsJson.printSchema();
        dsJson.show(false);
    }

}
