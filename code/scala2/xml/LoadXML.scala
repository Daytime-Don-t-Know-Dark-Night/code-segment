package scala2.xml

import scala.xml._

/**
 * @author chao
 * @date 2023/1/7 21:33
 * @desc
 */
object LoadXML {

    def main(args: Array[String]): Unit = {

        val path = "./scala/xml/user.xml"
        val xmlFile = XML.load(path)

        println("用户数: " + (xmlFile \\ "book").size)

        // 查找 bookId=001的元素

        var bookNodeSeq = (xmlFile \ "book").filter(x => (x \ "@id").text.equals("001"))
        var entity = new Entity
        entity.setName(bookNodeSeq.\("name").text)
        entity.setAge(bookNodeSeq.\("age").text)
        entity.setEmail(bookNodeSeq.\("email").text)

        println(entity)

    }

}
