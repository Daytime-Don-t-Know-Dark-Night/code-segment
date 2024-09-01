package java8.spark;

import com.clearspring.analytics.util.Lists;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.functions.*;

import java.util.List;

public class UDFunc2 {

	public static final ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) {

		SparkSession spark = SparkSession
				.builder()
				.master("local[*]")
				.getOrCreate();

		StructType schema = new StructType()
				.add("json", "string");

		Row json1 = RowFactory.create("[{\"extendInfo\":{\"d\":\"111\"},\"franchiseOrgCode\":\"1002\",\"franchiseOrgName\":\"专营机构名称\",\"handlerEmpNo\":\"557\",\"handlerName\":\"经办人\",\"handlerPerformanceRate\":1,\"higherFranchiseOrgCodePath\":\"1001\",\"higherFranchiseOrgNamePath\":\"上级专营机构名称\",\"higherOrgCodePath\":\"A1\",\"higherOrgNamePath\":\"浙江\",\"identity\":\"557\",\"isMainHandler\":1,\"jobLevelCd\":\"14\",\"orgCode\":\"A1-B3\",\"orgFranchiseTypeCd\":\"01\",\"orgName\":\"杭州\",\"orgTypeCd\":\"12\",\"positionSeqNo\":\"1\",\"saleTeamCode\":\"30100\",\"saleTeamName\":\"销售团队\"}]");
		List<Row> list = Lists.newArrayList();
		list.add(json1);

		Dataset<Row> ds = spark.createDataFrame(list, schema);
		ds.show(false);

		spark.udf().register("take", new UDF1<String, String>() {
			@Override
			public String call(String s) throws Exception {

				ArrayNode array = (ArrayNode) mapper.readTree(s);
				for (JsonNode obj : array) {
					int handle = ((ObjectNode) obj).at("/isMainHandler").asInt();
					if (handle == 1) {
						return ((ObjectNode) obj).at("/handlerEmpNo").asText();
					}
				}
				return null;
			}
		}, DataTypes.StringType);

		ds.withColumn("take_value", expr("take(json)")).show(false);
		System.out.println("---");

	}

}
