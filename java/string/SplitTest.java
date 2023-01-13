package string;

import java.util.Arrays;

/**
 * @author chao
 * @date 2023/1/13 21:47
 * @desc
 */
public class SplitTest {

    // https://www.cxyck.com/article/133322.html

    public static void main(String[] args) {
        String sql = "SELECT * FROM table_a WHERE id = '001' AND name = 'c'";
        // 按照关键字将SQL拆分开
        String[] sqls = sql.split("(?<=WHERE)|(?=WHERE)|(?<=AND)|(?=AND)");
        Arrays.stream(sqls).forEach(System.out::println);
    }

}
