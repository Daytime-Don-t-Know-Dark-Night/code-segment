package enums;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

/**
 * @author chao
 * @date 2022/11/10 21:32
 * @desc
 */
public class CompareUtils {

    private enum StatusEnum {
        success, warning, fail
    }

    public static StatusEnum status(Dataset<Row> sourceDs, Dataset<Row> targetDs) {
        Dataset<Row> sourceRes = sourceDs.exceptAll(targetDs);
        Dataset<Row> targetRes = targetDs.exceptAll(sourceDs);

        boolean success_br = sourceRes.isEmpty() && targetRes.isEmpty();
        boolean warning_br = sourceRes.isEmpty() && !targetRes.isEmpty();

        if (success_br) {
            return StatusEnum.success;
        } else if (warning_br) {
            return StatusEnum.warning;
        } else {
            return StatusEnum.fail;
        }
    }

    public static Boolean isSuccess(Dataset<Row> sourceDs, Dataset<Row> targetDs) {
        return status(sourceDs, targetDs).equals(StatusEnum.success);
    }

    public static Boolean isWarning(Dataset<Row> sourceDs, Dataset<Row> targetDs) {
        return status(sourceDs, targetDs).equals(StatusEnum.warning);
    }

    public static Boolean isFail(Dataset<Row> sourceDs, Dataset<Row> targetDs) {
        return status(sourceDs, targetDs).equals(StatusEnum.fail);
    }
}
