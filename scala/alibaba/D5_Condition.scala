package alibaba

/**
 * @Author dingc
 * @Date 2022-08-28 17:39
 * @Description
 */

object D5_Condition extends App {

    var faceValue = 98
    var res1 = if (faceValue > 90) "优秀" else "一般"
    println(res1)

    var i = 3
    var res2 = if (i > 5) i
    println(res2)

    val score = 75
    val res3 = if (score < 60) "不及格"
    else if (score >= 60 && score < 70) "及格"
    else if (score >= 70 && score < 80) "良好"
    else "优秀"

    println(res3)
}
