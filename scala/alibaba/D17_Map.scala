package alibaba

/**
 * @Author dingc
 * @Date 2022-08-30 21:16
 * @Description
 */
object D17_Map extends App {

    val array1 = Array(1, 2, 3, 4, 5)

    // x代表数组中的每个元素
    val z = array1.map((x: Int) => x * 2)
    // 这样写, 编译器会推断出数据的类型
    val y = array1.map(x => x * 2)
    // _代表数组中的每个元素
    val x = array1.map(_ * 2)


    val words = Array("tom jack oliver jack", "hello tom oliver tom")
    // TODO 将 words中元素按照,切分


}
