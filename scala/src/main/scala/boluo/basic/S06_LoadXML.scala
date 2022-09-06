package boluo.basic

import scala.xml._

/**
 * @Author dingc
 * @Date 2022-09-06 21:07
 * @Description
 */
object S06_LoadXML {

    // 加载xml文件
    def main(args: Array[String]): Unit = {

        val path = "C:\\Projects\\home\\parent\\doc\\test\\user.xml";
        val xmlFile = XML.load(path)

        println("用户数: " + (xmlFile \\ "book").size)

        // 查找 bookId=001的元素

        var bookNodeSeq = (xmlFile \ "book").filter(x => (x \ "@id").text.equals("001"))
        var entity = new S06_Entity
        entity.setName(bookNodeSeq.\("name").text)
        entity.setAge(bookNodeSeq.\("age").text)
        entity.setEmail(bookNodeSeq.\("email").text)

        println(entity)
    }


}
