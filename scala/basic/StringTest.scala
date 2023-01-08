package basic

/**
 * @Author dingc
 * @Date 2022-08-28 17:29
 * @Description
 */

// App是Scala自己提供的一个类, 当object继承它时, 不需要写main方法, 会将整个类看作一个main方法
object StringTest extends App {

    val name = "JackMa"
    val price = 999.88d
    val url = "www.google.com"

    // 与Java不同, 这里可以使用,隔开
    println("name: " + name, "price: " + price, "url: " + url)

    // f插值器允许创建一个格式化的字符串, 类似于C语言中的printf
    println(f"姓名: $name%s, 价格: $price%1.2f, 网址: $url%s")
    println(f"姓名: %%s, 价格: %%1.1f, 网址: %%s", name, price, url)

    // s插值器允许在字符串中直接使用变量
    println(s"name=$name, price=$price, url=$url")

    // s插值器还可以处理任意形式的表达式
    println(s"1+1=${1+1}")

}