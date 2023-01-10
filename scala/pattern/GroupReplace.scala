package pattern

import java.util.regex.Pattern

/**
 * @author chao
 * @date 2023/1/9 21:07
 * @desc
 */
object GroupReplace {

    def main(args: Array[String]): Unit = {
        val newSql = func1()
        println(newSql)
    }

    def func1(): String = {
        val startDate = "2023/01/01"
        val endDate = "2023/02/01"
        // 所有关键字使用大写, 时间放在WHERE后第一位
        val sql = "( SELECT * FROM table_a WHERE COA_DATE = TO_DATE('COA_DATE_REPLACER', 'yyyy/mm/dd') AND fxh.wss_tid = fx.wss_tid AND fx.ticket_area='AREA_REPLACER' AND $CONDITIONS ) BO_ORD_FXHIST"
        val outerPattern = Pattern.compile("(.*SELECT.*?WHERE)(.*'COA_DATE_REPLACER'.*?)(AND.*)")
        val outerMatcher = outerPattern.matcher(sql)
        if (outerMatcher.find()) {
            val timeCondition = outerMatcher.group(2)
            val innerPattern = Pattern.compile("(.*)=(.*'COA_DATE_REPLACER'.*)")
            val innerMatcher = innerPattern.matcher(timeCondition)
            assert(innerMatcher.find())
            val coaDateCol = timeCondition.substring(0, timeCondition.indexOf("="))
            val newTimeCondition = String.format(" %s >= TO_DATE('%s', 'yyyy/mm/dd') AND %s < TO_DATE('%s', 'yyyy/mm/dd') ", coaDateCol, startDate, coaDateCol, endDate)
            return outerMatcher.group(1) + newTimeCondition + outerMatcher.group(3)
        }
        null
    }

}
