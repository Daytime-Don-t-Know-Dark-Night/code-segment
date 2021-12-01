package boluo.basic

object S02_Variable {
    def main(args: Array[String]): Unit = {

        // 变量
        var myVar1: String = "Foo"
        myVar1 = "Bar"

        // 常量
        val myVar2 = "Too"

        // 在Scala中声明变量和常量不一定要指明数据类型, 在没有指明数据类型的情况下, 其数据类型是通过变量或常量的初始值推断出来的
        // 所以, 如果在没有指明数据类型的情况下声明变量或常量必须要给出初始值, 否则会报错
        var myVar3 = 10
        val myVar4 = "Too"

        // 支持多个变量的声明
        val x, y = 100

        // 如果方法返回值是元组, 我们可以使用val来声明一个元组
        val pa = (40, "Foo")
        print(pa)
    }
}
