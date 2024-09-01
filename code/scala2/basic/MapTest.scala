package scala2.basic

import scala.collection.mutable

/**
 * @author chao
 * @date 2023/1/16 21:39
 * @desc
 */
object MapTest {

    // https://nummy-demo.readthedocs.io/zh_CN/latest/chapter/chapter05.html#id1

    def main(args: Array[String]): Unit = {
        func4()
    }

    // 不可变映射(Map)
    def func1(): Unit = {
        val score = Map("dingc" -> 80)
    }

    // 可变映射(Map)
    def func2(): Unit = {
        val score = mutable.Map("dingc" -> 80)
        println("score初始值: " + score("dingc"))

        // 可变映射更新值
        score("dingc") = 90
        println("更新之后, score值: " + score("dingc"))

        // 映射中添加元素
        score += ("boluo" -> 90)
        println("添加一个元素: " + score.mkString(","))

        // 映射中删除元素
        score -= ("boluo")
        println("移除一个元素: " + score.mkString(","))
    }

    // 循环
    def func3(): Unit = {
        // 有序映射
        val scores = mutable.LinkedHashMap(
            "qqq" -> 111,
            "www" -> 222,
            "eee" -> 333,
            "rrr" -> 444
        )
        println("scores: " + scores.mkString(","))
        // 循环遍历
        for ((k, v) <- scores) {
            println(s"key: $k, value: $v")
        }
    }

    // List 转 Map
    def func4(): Unit = {
        val list1 = List("ta", "tb", "tc", "td", "te", "tf", "tg")
        var idx = 1
        val map: mutable.LinkedHashMap[String, Int] = mutable.LinkedHashMap.empty
        list1.foreach(i => {
            map.put(i, idx)
            idx = idx + 1
        })
        println(map)
    }

}
