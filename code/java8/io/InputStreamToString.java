package java8.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author chao
 * @date 2022/12/15 10:23
 * @desc
 */
public class InputStreamToString {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("C:\\Users\\chao\\IdeaProjects\\dingx\\doc\\txt\\1.txt"));
        String res = inputStream2String(inputStream, 2);
        System.out.println(res);
    }

    public static String inputStream2String(InputStream ins) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = ins.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        ins.close();
        return outSteam.toString();
    }

    public static String inputStream2String(InputStream ins, int idx) throws IOException {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        do {
            len = ins.read(buffer);
            if (len == -1) {
                break;
            }
            outSteam.write(buffer, 0, len);
        } while (true);
        outSteam.close();
        ins.close();
        return outSteam.toString();
    }


}
