package java8.string;

import com.google.common.base.CharMatcher;

/**
 * @author chao
 * @date 2023/1/2 15:19
 * @desc
 */
public class CharMatcherTest {

    // https://www.cnblogs.com/liang1101/p/13638186.html

    public static void main(String[] args) {
        func1();
    }

    public static void func1() {
        String str = "a/bb/ccc/dddd/eeeee";
        // 查找最后一个'/'字符的索引
        int idx = CharMatcher.anyOf("/").lastIndexIn(str);
        System.out.println("最后一个'/'字符的索引为: " + idx);
    }

}
