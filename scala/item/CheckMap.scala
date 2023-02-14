package item

/**
 * @author chao
 * @date 2023/2/14 21:35
 * @desc
 */
object CheckMap {

    def main(args: Array[String]): Unit = {
        val map1 = Map("a" -> "1", "b" -> "2", "c" -> "3")
        val map2 = Map("a" -> "1", "b" -> "2", "d" -> "4")
        val result = mapsDifference(map1, map2)
        println("result: " + result)
    }

    def mapsDifference(map1: Map[String, String], map2: Map[String, String]): Map[String, (Option[String], Option[String])] = {
        if (map1 == map2) {
            null
        } else {
            val keys = map1.keySet ++ map2.keySet
            List("a").foldLeft()
            keys.foldLeft(Map[String, (Option[String], Option[String])]()) { (diff, key) =>
                (map1.get(key), map2.get(key)) match {
                    case (Some(value1), Some(value2)) if value1 != value2 => diff + (key -> (Some(value1), Some(value2)))
                    case (None, Some(value2)) => diff + (key -> (None, Some(value2)))
                    case (Some(value1), None) => diff + (key -> (Some(value1), None))
                    case _ => diff
                }
            }
        }
    }


}
