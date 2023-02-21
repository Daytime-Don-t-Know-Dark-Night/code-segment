package basic

/**
 * @author chao
 * @date 2023/2/21 22:09
 * @desc
 */
object MatchTest {

    // scala的match表达式类似于switch语句, 提供多个备选项中做选择.

    def main(args: Array[String]): Unit = {
        func1("apple")
        func3("apple")
    }

    // 1. 按值匹配
    def func1(fruit: String): Unit = {
        fruit match {
            case "apple" =>
                println("this is an apple!!")
            case "banana" =>
                println("this is a banana!!")
            case "orange" =>
                println("this is an orange!!")
            // _ 可以匹配任意
            case _ =>
                println("unknown!!")
        }
    }

    // 2. 按对象类型匹配
    def func2(obj: Any): Unit = {
        obj match {
            case x: Int =>
                println(x + " is int!!")
            case x: String =>
                println(x + " is string!!")
            case x: List[String] =>
                println(x + " is list!!")
            case _ =>
                println("unknown!!")
        }
    }

    // 3. 返回值
    def func3(fruit: String): Unit = {
        val name = fruit match {
            case "apple" => "Apple"
            case "banana" => "Banana"
            case "orange" => "orange"
            case _ => "unknown"
        }
        println("name value is: " + name)
    }

}
