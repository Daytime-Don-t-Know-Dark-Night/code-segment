package com.boluo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author dingc
 * @Date 2022-07-21 22:23
 * @Description 描述
 * 写出一个程序，接受一个由字母、数字和空格组成的字符串，和一个字符，然后输出输入字符串中该字符的出现次数。（不区分大小写字母）
 * 数据范围： 1 < n <= 1000
 * <p>
 * 输入描述：
 * 第一行输入一个由字母和数字以及空格组成的字符串，第二行输入一个字符。
 * <p>
 * 输出描述：
 * 输出输入字符串中含有该字符的个数。（不区分大小写字母）
 */

public class HJ2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> bes = br.lines().collect(Collectors.toList());
        String str = bes.get(0);
        String s = bes.get(1).toLowerCase();

        int sum = 0;
        for (char c : str.toLowerCase().toCharArray()) {
            if (c == s.charAt(0)) {
                sum++;
            }
        }

        System.out.println(sum);
    }
}
