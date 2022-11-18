package boluo.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chao
 * @date 2022/11/18 21:00
 * @desc
 */
public class NumberPattern {

    public static void main(String[] args) {

        String normal = "part-m-r_bo_ord_dc-20221218210102123456.avro";
        String replay = "part-m-r_bo_ord_dc-20221218210102.avro";

        String normalResult = func(normal);
        String replayResult = func(replay);

        System.out.println(normalResult);
        System.out.println(replayResult);

    }

    private static String func(String str) {
        Pattern patternNormal = Pattern.compile("\\S*-(\\d*).avro$");
        // Pattern patternReplay = Pattern.compile("\\S*-(\\d{14}).avro$");
        Matcher matcher = patternNormal.matcher(str);
        if (matcher.find()) {
            return matcher.group(1).substring(0, 10);
        }
        return null;
    }
}
