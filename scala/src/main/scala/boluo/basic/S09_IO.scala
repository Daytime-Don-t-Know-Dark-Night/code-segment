package boluo.basic

import java.io.{ByteArrayOutputStream, InputStream}
import java.nio.file.{Files, Paths}
import scala.util.control._

/**
 * @author chao
 * @date 2022/12/15 11:07
 * @desc
 */
object S09_IO {

    def main(args: Array[String]): Unit = {
        val inputStream = Files.newInputStream(Paths.get("C:\\Users\\chao\\IdeaProjects\\dingx\\doc\\txt\\1.txt"))
        val res = inputStream2String(inputStream)
        System.out.println(res)
    }

    def inputStream2String(ins: InputStream): String = {
        val outStream = new ByteArrayOutputStream()
        val buffer: Array[Byte] = new Array[Byte](1024)

        var len: Int = 0
        var flag = true
        do {
            len = ins.read(buffer)
            if (len != -1) {
                outStream.write(buffer, 0, len)
            }
            flag = false
        } while (flag)

        outStream.close()
        ins.close()
        outStream.toString()
    }

}
