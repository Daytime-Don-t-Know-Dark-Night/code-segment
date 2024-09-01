package java8.date;

import java.time.LocalDateTime;

/**
 * @author chao
 * @date 2022/12/30 14:23
 * @desc
 */
public class LocalDateTimeTest {

    public static void main(String[] args) {
        func1();
    }

    // 时间循环
    public static void func1() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusDays(10L);

        int k = 1;
        for (LocalDateTime start = startTime; start.compareTo(endTime) <= 0; start = startTime.plusDays(k++)) {
            System.out.println(start);
        }
    }

}
