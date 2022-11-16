package landeli;

import boluo.enums.CompareUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.collect.ImmutableList;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import scala.Tuple2;
import scala.Tuple3;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;


/**
 * @author chao
 * @date 2022/11/16 21:44
 * @desc
 */
public class FuncTest {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final SparkSession spark = SparkSession
            .builder().master("local[*]")
            .config("spark.driver.host", "localhost")
            .getOrCreate();

    @Test
    public void enumTest() {

        Dataset<String> ds = spark.createDataset(ImmutableList.of("1", "2", "3"), Encoders.STRING());
        ds.show(false);

        Dataset<Row> ds1 = spark.createDataset(ImmutableList.of(
                        RowFactory.create("1", "dingc"),
                        RowFactory.create("2", "boluo"),
                        RowFactory.create("3", "qidai")),
                RowEncoder.apply(new StructType().add("id", DataTypes.StringType).add("name", DataTypes.StringType))
        );
        Dataset<Row> ds2 = spark.createDataset(ImmutableList.of(
                        RowFactory.create("1", "dingc"),
                        RowFactory.create("2", "boluo"),
                        RowFactory.create("3", "chuixue")),
                RowEncoder.apply(new StructType().add("id", DataTypes.StringType).add("name", DataTypes.StringType))
        );

        Assert.assertFalse(CompareUtils.isSuccess(ds1, ds2));
        Assert.assertFalse(CompareUtils.isWarning(ds1, ds2));
        Assert.assertTrue(CompareUtils.isFail(ds1, ds2));
    }

}
