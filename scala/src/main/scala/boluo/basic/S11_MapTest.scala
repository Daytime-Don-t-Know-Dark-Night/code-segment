package boluo.basic

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * @author chao
 * @date 2022/12/16 13:34
 * @desc
 */
object S11_MapTest {

    def main(args: Array[String]): Unit = {
        val statusMap: mutable.Map[String, ListBuffer[String]] = mutable.Map(
            "success" -> ListBuffer.empty,
            "warning" -> ListBuffer.empty,
            "fail" -> ListBuffer.empty
        )

        insertStatus(statusMap)
        println(statusMap)
    }

    def insertStatus(statusMap: mutable.Map[String, ListBuffer[String]]): Unit = {
        val list: ListBuffer[String] = statusMap("success")
        val s = list.mkString(",")
        println(s)
        list += "D"
        statusMap.put("success", list)
    }
}
