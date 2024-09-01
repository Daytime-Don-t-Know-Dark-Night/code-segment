package java8.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chao
 * @datetime 2024-08-21 22:57
 * @description
 */
public class Regex {

    private static String jsonStr = "{\"key\": [{\"id\":1,\"name\":\"dingc\",\"num1\":\"13\",\"num2\":-13,\"num3\":13.123456789012345678,\"num4\":-13.14}]}";

    public static void main(String[] args) {

        // 正则表达式，匹配 JSON 字符串中的数字（包括正负整数和浮点数）
        String regex = "(?<=:)(-?\\d+(\\.\\d+)?)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jsonStr);

        // 使用 StringBuffer 存储修改后的字符串
        StringBuffer sb = new StringBuffer();

        // 查找并替换匹配的数字
        while (matcher.find()) {
            // 获取匹配的数字
            String matchedNumber = matcher.group();
            // 在数字前后添加双引号
            String quotedNumber = "\"" + matchedNumber + "\"";
            // 替换数字为带双引号的数字
            matcher.appendReplacement(sb, quotedNumber);
        }
        matcher.appendTail(sb);
        System.out.println(sb);
        System.out.println();

        // {"key": [{"id":"1","name":"dingc","num1":"13","num2":"-13","num3":"13.123456789012345678","num4":"-13.14"}]}

    }
}
