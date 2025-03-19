package com.boluo.core.date;

import java.time.Duration;
import java.time.Period;

/**
 * @author chao
 * @date 2022/11/7 22:12
 * @desc
 */
public class PeriodTest {

    public static void main(String[] args) {

        String day3 = "3d";
        Period parseDay3 = Period.parse("P" + day3);
        System.out.println(parseDay3.toString());

        String hour3 = "PT3h";
        Duration durationHour3 = Duration.parse(hour3);
        System.out.println(durationHour3.toHours());

    }

}
