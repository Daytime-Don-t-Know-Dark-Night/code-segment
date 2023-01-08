package boluo.basic

/**
 * @author chao
 * @date 2022/12/15 21:03
 * @desc
 */
object Implicit {

    // 1.隐式值 && 隐式参数
    def main2(args: Array[String]): Unit = {
        implicit val name: String = "ding chao"
        sayName
    }

    def main3(args: Array[String]): Unit = {
        implicit val name: String = "ding chao"
        information(19)
    }

    def sayName(implicit name: String): Unit = {
        println(name + " is a student ... ")
    }


    def information(age: Int)(implicit name: String): Unit = {
        println("name is " + name + ", age is " + age)
    }



    // 2.隐式转换函数
    class Animal(name: String) {
        def fly(): Unit = {
            println(name + " can fly")
        }
    }

    class Person(pName: String) {
        val name = pName
    }

    implicit def animal2Person(person: Person): Animal = {
        new Animal(person.name)
    }

    def main(args: Array[String]): Unit = {
        val p = new Person("person_name")
        p.fly()
    }



    // 3.隐式类
    // https://blog.csdn.net/qq_44973159/article/details/106139335?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-1-106139335-blog-81064721.pc_relevant_3mothn_strategy_and_data_recovery&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-1-106139335-blog-81064721.pc_relevant_3mothn_strategy_and_data_recovery&utm_relevant_index=1


}


