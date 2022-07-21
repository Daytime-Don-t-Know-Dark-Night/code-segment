package com.boluo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author dingc
 * @Date 2022-07-21 22:30
 * @Description 描述
 * 明明生成了N个1到500之间的随机整数。请你删去其中重复的数字，即相同的数字只保留一个，把其余相同的数去掉，然后再把这些数从小到大排序，按照排好的顺序输出。
 * 数据范围： 1 <= n <= 1000 ，输入的数字大小满足 1 <= val <= 500
 * <p>
 * 输入描述：
 * 第一行先输入随机整数的个数 N 。 接下来的 N 行每行输入一个整数，代表明明生成的随机数。 具体格式可以参考下面的"示例"。
 * <p>
 * 输出描述：
 * 输出多行，表示输入数据处理后的结果
 */
public class HJ3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> collect = br.lines().collect(Collectors.toList());
        int i = Integer.parseInt(collect.get(0));
        List<String> li2 = collect.subList(1, collect.size());
        List<String> res = li2.stream().distinct().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1) - Integer.parseInt(o2);
            }
        }).collect(Collectors.toList());

        for (String s : res) {
            System.out.println(s);
        }
    }
}
