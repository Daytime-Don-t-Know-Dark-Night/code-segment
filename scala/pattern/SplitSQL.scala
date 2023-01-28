package pattern

import java.util.regex.Pattern

/**
 * @author chao
 * @date 2023/1/28 20:44
 * @desc
 */
object SplitSQL {

    def main(args: Array[String]): Unit = {
        func1()
    }

    def func1(): Unit = {
        val sql = "( SELECT * FROM table_a WHERE (TO_DATE(DATE) = TO_DATE('DATE_REPLACER', 'yyyy/mm/dd') AND fxh.wss_tid = fx.wss_tid) AND fx.ticket_area='AREA_REPLACER' AND $CONDITIONS ) BO_ORD_FXHIST"
        val sqls = sql.split(
            "(?<= WHERE )|(?= WHERE )|(?<= where )|(?= where )" +
                "(?<= AND )|(?= AND )|(?<= and )|(?= and )|" +
                "(?<= OR )|(?= OR )|(?<= or )|(?= or )|" +
                "(?<= UNION )|(?= UNION )|(?<= union )|(?= union )"
        )

        val array = sqls.map(i => {
            if (i.contains("'DATE_REPLACER'")) {
                // 提取出( 后的 TO_DATE(DATE) = TO_DATE('DATE_REPLACER', 'yyyy/mm/dd')单独处理, 将前后多余的 ( ) 和中间的核心语句分离开
                // ( a = b
                // a = b )
                // ( date(a) = date(b)

                i
            } else {
                i
            }
        })

        array.foreach(println(_))
    }

}
